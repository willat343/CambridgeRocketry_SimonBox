//MonteFy.h
#ifndef MonteFy_H
#define MonteFy_H

#include<cmath>
#include<string>
#include "vmaths.h"
#include "intabread.h"
#include <boost/property_tree/ptree.hpp>
#include <boost/property_tree/xml_parser.hpp>
#include <boost/foreach.hpp>
#include <boost/numeric/ublas/matrix.hpp>
#include <boost/numeric/ublas/vector.hpp>
#include <boost/numeric/ublas/io.hpp>
#include <boost/algorithm/string.hpp>
#include <boost/lexical_cast.hpp>
#include <boost/random.hpp>
#include <ctime>
#include "interpolation.h"
#include "intabread.h"
#include "vectorops.h"

class MonteFy{
public:
		INTAB SinTab;
		double CDm,
			CoPm,
			CNm,
			CDdm,
			CDpm,
			sigmaLaunchDeclination, // stochastic variable for launch angle
			sigmaThrust; // stochastic variable for thrust
		boost::numeric::ublas::vector<double> Mu;
		boost::numeric::ublas::vector<double> HScale;
		boost::numeric::ublas::matrix<double> Sigma;
		boost::numeric::ublas::matrix<double> EigenValue;
		boost::numeric::ublas::matrix<double> EigenVector;
		boost::numeric::ublas::matrix<double> PHI;
		boost::property_tree::ptree PropTree;

		MonteFy(){};
		MonteFy(INTAB,string);
		boost::numeric::ublas::vector<double> gsamp(void);
		double SampleNormal(double mean, double sigma);
		double SampleNormalTruncated(double mean, double sigma, double truncateSigma);
		void ReadInVector(boost::numeric::ublas::vector<double>* ,string,int);
		void ReadInMatrix(boost::numeric::ublas::matrix<double>*,string,int,int);
		INTAB Wiggle();
		INTAB WiggleProtect(INTAB thisIntab);
		std::vector<double> ToStdVec (boost::numeric::ublas::vector<double>);
};





#endif