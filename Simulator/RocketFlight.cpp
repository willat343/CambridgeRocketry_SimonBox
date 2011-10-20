//RocketFlight.cpp

#include"RocketFlight.h"


//Defing Constructors******************************************************************

Rocket_Flight::Rocket_Flight()
{
	X0.e1=0.0; X0.e2=0.0; X0.e3=0.0;//default initial position
	RL=2.0;                         //default rail length
	Az=0.0;                         //default rail azimuth
	De=0.0;							//default rail declination
	Tspan=1000;						//default maximum simulation time
	ballisticfailure=false;			//default setting flight terminates at apogee
	ShortData=false;			    //default output data format is long
}

Rocket_Flight::Rocket_Flight(INTAB IT1)
{
	IntabTR=IT1;
	X0.e1=0.0; X0.e2=0.0; X0.e3=0.0;//default initial position
	RL=2.0;                         //default rail length
	Az=0.0;                         //default rail azimuth
	De=0.0;							//default rail declination
	Tspan=1000;						//default maximum simulation time
	ballisticfailure=false;			//default setting flight terminates at apogee
	ShortData=false;			    //default output data format is long
}

Rocket_Flight::Rocket_Flight(INTAB IT1, INTAB IT2, INTAB IT3, double d1, double d2)
{
	IntabTR=IT1;
	IntabBS=IT2;
	IntabUS=IT3;
	Tsep=d1;
	IgDelay=d2;
	X0.e1=0.0; X0.e2=0.0; X0.e3=0.0;//default initial position
	RL=2.0;                         //default rail length
	Az=0.0;                         //default rail azimuth
	De=0.0;							//default rail declination
	Tspan=1000;						//default maximum simulation time
	ballisticfailure=false;			//default setting flight terminates at apogee
	ShortData=false;			    //default output data format is long
}

Rocket_Flight::Rocket_Flight(std::string)
{
	//Impliment later
}

//****************************************************************************************

//Function to calculate the initial conditions********************************************

void Rocket_Flight::InitialConditionsCalc(void)
{
	tt.push_back(0);
	tt.push_back(Tspan);

	z0.push_back(X0.e1);
	z0.push_back(X0.e2);
	z0.push_back(X0.e3);

	//Calculate the initial quaternion
	De=De*PI/180.0;
	bearing AzOrth((Az-90.0),1.0);
	vector2 AzVec=AzOrth.to_vector();
	AzVec=AzVec.norm();

	quaternion Q0((cos(De/2.0)),(sin(De/2.0)*AzVec.e1),(sin(De/2.0)*AzVec.e2),0.0);
	Q0=Q0.norm();

	z0.push_back(Q0.e1);
	z0.push_back(Q0.e2);
	z0.push_back(Q0.e3);
	z0.push_back(Q0.e4);

	for (int i = 0; i<6; i++){
		z0.push_back(0.0);
	}
}

//Flight Functions**********************************************************************

OutputData Rocket_Flight::OneStageFlight(void){

	//Create the initial conditions 
	InitialConditionsCalc();
	
	//Initialize rocket ascent simulation
	ascent as1(tt,z0,IntabTR,RL);

	BallisticSwitch(&as1);
	
	//Fly the rocket!
	RKF_data updata=as1.fly();


	//Take the final state of the rocket to define the 
	//initial conditions for the parachute
	vector<double> tt2, z02;
	ParachuteTransfer(&tt2,&z02,updata);


	//Initialize parachute descent simulation
	descent ds1(tt2,z02,IntabTR);
	RKF_data downdata=ds1.fall();

	OutputData OD;
	OD.InitializePropertyTree("OneStageFlight");
	
	if (ShortData == true){
		FlightDataShort FDS = (as1.getShortData() + ds1.getShortData());
		OD.FillPropertyTree(&FDS,1);
	}
	else{
		FlightDataLong FDL = (as1.getLongData() + ds1.getLongData());
		OD.FillPropertyTree(&FDL,1);  
	}

        return(OD);
        //OD.WriteToXML("SimulationOutput.xml");

}

OutputData Rocket_Flight::TwoStageFlight(){

	//Create the initial conditions
	InitialConditionsCalc();

	//Initialize the simulation from launch to separation
	tt[1]=Tsep;

	ascent as1(tt,z0,IntabTR,RL);

	//Fly the first stage
	RKF_data StageTR = as1.fly();


	//Take the final state of StageTR to define
	//the initial conditions for the post separation
	//stages
	vector<double>tt2, z02_BS, z02_US;
	StageTransfer(&tt2,&z02_BS,&z02_US,StageTR);

	//Initialize the simulation of the booster stage
	ascent as2(tt2,z02_BS,IntabBS,0.0);

	//Fly the booster stage
	RKF_data StageBR = as2.fly();

	//Add ignition delay to upper stage
	INTAB IntabUS_t = IntabUS;
	IntabUS_t.intab1.addDelay(Tsep+IgDelay);

	ascent as3(tt2,z02_US,IntabUS_t,0.0);

	//Fly the upper stage
	RKF_data StageUS = as3.fly();


	//Take the final flight states for the two flight stages
	//and use them to prepare the parachute stages

	//Booster Stage
	vector<double>tt3, z03;
	ParachuteTransfer(&tt3,&z03,StageBR);

	//Upper Stage
	vector<double>tt4, z04;
	ParachuteTransfer(&tt4,&z04,StageUS);
	
	//Simulate the descent stages************************
	//Booster stage
	descent ds1(tt3,z03,IntabBS);

	RKF_data RecoverBS = ds1.fall();

	//Upper stage
	descent ds2(tt4,z04,IntabUS);

	RKF_data RecoverUS = ds2.fall();

	///Package and output

	OutputData OD;
	OD.InitializePropertyTree("TwoStageFlight");
	vector<FlightData *> FDp;

	if (ShortData == true){
		FlightDataShort FDSL = (as1.getShortData() + as2.getShortData() + ds1.getShortData());
		FlightDataShort FDSU = (as1.getShortData() + as3.getShortData() + ds2.getShortData());	
		FDp.push_back(&FDSL);
		FDp.push_back(&FDSU);
		OD.FillPropertyTree(FDp,1);
	}
	else{
		FlightDataLong FDLL = (as1.getLongData() + as2.getLongData() + ds1.getLongData());
		FlightDataLong FDLU = (as1.getLongData() + as3.getLongData() + ds2.getLongData());
		FDp.push_back(&FDLL);
		FDp.push_back(&FDLU);
		OD.FillPropertyTree(FDp,1);
	}
	
	return(OD);
        //OD.WriteToXML("SimulationOutput.xml");

}

OutputData Rocket_Flight::OneStageMonte(int noi){

	//Create the initial conditions 
	InitialConditionsCalc();
        string Upath = FilePath;
        Upath.append("Uncertainty.xml");
	MonteFy StochTab(IntabTR,Upath);
	
	OutputData OD;
	OD.InitializePropertyTree("OneStageMonte");
	int Frun = 0;
	
	for(int i=0; i<noi;i++){
		//Initialize rocket ascent simulation
		try{
			INTAB Ftab;
			if(i == 0){
				Ftab = IntabTR;
			}
			else{
				Ftab = StochTab.Wiggle();
				//Ftab = IntabTR;
			}
			ascent as1(tt,z0,Ftab,RL);
			BallisticSwitch(&as1);
			
			//Fly the rocket!
			RKF_data updata=as1.fly();

			//Take the final state of the rocket to define the 
			//initial conditions for the parachute
			vector<double> tt2, z02;
			ParachuteTransfer(&tt2,&z02,updata);

			//Initialize parachute descent simulation
			descent ds1(tt2,z02,IntabTR);
			RKF_data downdata=ds1.fall();

			if (ShortData == true){
				FlightDataShort FDS = (as1.getShortData() + ds1.getShortData());
				OD.FillPropertyTree(&FDS,(i-Frun+1));
			}
			else{
				FlightDataLong FDL = (as1.getLongData() + ds1.getLongData());
				OD.FillPropertyTree(&FDL,(i-Frun+1));
			}
		
		}
		catch(exception e){
			Frun++;
			cout<<"Run failed, number of faild runs is: "<<Frun<<endl;

		}

	}
	return(OD);
        //OD.WriteToXML("SimulationOutput.xml");
	
	
}

OutputData Rocket_Flight::TwoStageMonte(int noi){
	//Create the initial conditions
	InitialConditionsCalc();
	//Initialize the simulation from launch to separation
	tt[1]=Tsep;
        string Upath = FilePath;
        Upath.append("Uncertainty.xml");
	MonteFy StochTabTR(IntabTR,Upath);
	MonteFy StochTabBS(IntabBS,Upath);
	MonteFy StochTabUS(IntabUS,Upath);
	OutputData OD;
	OD.InitializePropertyTree("TwoStageMonte");
	int Frun=0;

	for (int i =0; i<noi; i++){
		try{
			INTAB FtabTR, FtabBS, FtabUS;
			if(i == 0){
				FtabTR = IntabTR;
				FtabBS = IntabBS;
				FtabUS = IntabUS;
			}
			else{
				FtabTR = StochTabTR.Wiggle();
				FtabBS = StochTabBS.Wiggle();
				FtabUS = StochTabUS.Wiggle();
			}

			ascent as1(tt,z0,FtabTR,RL);

			//Fly the first stage
			RKF_data StageTR = as1.fly();


			//Take the final state of StageTR to define
			//the initial conditions for the post separation
			//stages
			vector<double>tt2, z02_BS, z02_US;
			StageTransfer(&tt2,&z02_BS,&z02_US,StageTR);

			//Initialize the simulation of the booster stage
			ascent as2(tt2,z02_BS,FtabBS,0.0);

			//Fly the booster stage
			RKF_data StageBR = as2.fly();

			//Add ignition delay to upper stage
			INTAB FtabUS_t = FtabUS;
			FtabUS_t.intab1.addDelay(Tsep+IgDelay);

			ascent as3(tt2,z02_US,FtabUS_t,0.0);

			//Fly the upper stage
			RKF_data StageUS = as3.fly();


			//Take the final flight states for the two flight stages
			//and use them to prepare the parachute stages

			//Booster Stage
			vector<double>tt3, z03;
			ParachuteTransfer(&tt3,&z03,StageBR);

			//Upper Stage
			vector<double>tt4, z04;
			ParachuteTransfer(&tt4,&z04,StageUS);
			
			//Simulate the descent stages************************
			//Booster stage
			descent ds1(tt3,z03,FtabBS);

			RKF_data RecoverBS = ds1.fall();

			//Upper stage
			descent ds2(tt4,z04,FtabUS);

			RKF_data RecoverUS = ds2.fall();

			///Package and output
			vector<FlightData *> FDp;

			if (ShortData == true){
				FlightDataShort FDSL = (as1.getShortData() + as2.getShortData() + ds1.getShortData());
				FlightDataShort FDSU = (as1.getShortData() + as3.getShortData() + ds2.getShortData());	
				FDp.push_back(&FDSL);
				FDp.push_back(&FDSU);
				OD.FillPropertyTree(FDp,(i-Frun+1));
			}
			else{
				FlightDataLong FDLL = (as1.getLongData() + as2.getLongData() + ds1.getLongData());
				FlightDataLong FDLU = (as1.getLongData() + as3.getLongData() + ds2.getLongData());
				FDp.push_back(&FDLL);
				FDp.push_back(&FDLU);
				OD.FillPropertyTree(FDp,(i-Frun+1));
			}
		}
		catch(exception e){
			Frun++;
			cout<<"Run failed, number of faild runs is: "<<Frun<<endl;
		}
	

	}

	
	return(OD);
        //OD.WriteToXML("SimulationOutput.xml");
}

void Rocket_Flight::ParachuteTransfer(std::vector<double> * tt2, std::vector<double> * z2, RKF_data RockStage){
	tt2->push_back(RockStage.t.back());
	tt2->push_back(RockStage.t.back()+Tspan);
	z2->push_back(RockStage.z.back()[0]);
	z2->push_back(RockStage.z.back()[1]);
	z2->push_back(RockStage.z.back()[2]);
	z2->push_back(RockStage.z.back()[7]);
	z2->push_back(RockStage.z.back()[8]);
	z2->push_back(RockStage.z.back()[9]);

}

void Rocket_Flight::StageTransfer(vector<double> * ttp, vector<double> * zp_BS, vector<double> * zp_US, RKF_data Stage){
	ttp->push_back(Stage.t.back());
	ttp->push_back(Stage.t.back()+Tspan);
	
	vector3 TransitionMomentum(Stage.z.back()[7],Stage.z.back()[8],Stage.z.back()[9]);
	vector3 TransitionVelocity = TransitionMomentum/IntabTR.intab1.Mass.back();
	
	vector3 Momentum_BS = TransitionVelocity*IntabBS.intab1.Mass.front();
	vector3 Momentum_US = TransitionVelocity*IntabUS.intab1.Mass.front();
	
	vector3 TransitionAngMom(Stage.z.back()[10],Stage.z.back()[11],Stage.z.back()[12]);
	quaternion TransitionQ(Stage.z.back()[3],Stage.z.back()[4],Stage.z.back()[5],Stage.z.back()[6]);
	matrix3x3 TransitionR = TransitionQ.to_matrix();

	matrix3x3 TransitionTensor(IntabTR.intab1.Ixx.back(),IntabTR.intab1.Ixy.back(),IntabTR.intab1.Ixz.back(),IntabTR.intab1.Ixy.back(),IntabTR.intab1.Iyy.back(),IntabTR.intab1.Iyz.back(),IntabTR.intab1.Ixz.back(),IntabTR.intab1.Iyz.back(),IntabTR.intab1.Izz.back());
	matrix3x3 TransitionInverse = TransitionR*TransitionTensor.inv()*TransitionR.transpose();
	vector3 TransitionOmega = TransitionInverse*TransitionAngMom;

	matrix3x3 Tensor_BS(IntabBS.intab1.Ixx.front(),IntabBS.intab1.Ixy.front(),IntabBS.intab1.Ixz.front(),IntabBS.intab1.Ixy.front(),IntabBS.intab1.Iyy.front(),IntabBS.intab1.Iyz.front(),IntabBS.intab1.Ixz.front(),IntabBS.intab1.Iyz.front(),IntabBS.intab1.Izz.front());
	matrix3x3 Tensor_US(IntabUS.intab1.Ixx.front(),IntabUS.intab1.Ixy.front(),IntabUS.intab1.Ixz.front(),IntabUS.intab1.Ixy.front(),IntabUS.intab1.Iyy.front(),IntabUS.intab1.Iyz.front(),IntabUS.intab1.Ixz.front(),IntabUS.intab1.Iyz.front(),IntabUS.intab1.Izz.front());

	matrix3x3 Inverse_BS = TransitionR*Tensor_BS.inv()*TransitionR.transpose();
    matrix3x3 Inverse_US = TransitionR*Tensor_US.inv()*TransitionR.transpose();

	vector3 AngMom_BS = Inverse_BS.inv()*TransitionOmega;
	vector3 AngMom_US = Inverse_US.inv()*TransitionOmega;


	for (int i=0; i<7; i++){
		zp_BS->push_back(Stage.z.back()[i]);
		zp_US->push_back(Stage.z.back()[i]);
	}

	zp_BS->push_back(Momentum_BS.e1);
	zp_BS->push_back(Momentum_BS.e2);
	zp_BS->push_back(Momentum_BS.e3);

	zp_US->push_back(Momentum_US.e1);
	zp_US->push_back(Momentum_US.e2);
	zp_US->push_back(Momentum_US.e3);

	zp_BS->push_back(AngMom_BS.e1);
	zp_BS->push_back(AngMom_BS.e2);
	zp_BS->push_back(AngMom_BS.e3);

	zp_US->push_back(AngMom_US.e1);
	zp_US->push_back(AngMom_US.e2);
	zp_US->push_back(AngMom_US.e3);

}

void Rocket_Flight::BallisticSwitch(ascent * a1){
	if (ballisticfailure==true){
		a1->Kill.index =2 ;      //Change the stop flag settings so that the 
		a1->Kill.Kval = -0.01;  //the ground
	}

}

void Rocket_Flight::setFilePath(string path){
    FilePath = path;
}







