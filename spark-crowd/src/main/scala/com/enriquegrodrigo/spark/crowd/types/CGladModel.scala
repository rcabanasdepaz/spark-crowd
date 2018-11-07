package com.enriquegrodrigo.spark.crowd.types

import org.apache.spark.sql._
import org.apache.spark.broadcast.Broadcast

/**
*  CGlad model returned by the CGlad method 
*
*  @param mu label estimation returned from the model.
*  @param alphas precision of annotator given by the CGlad model.
*  @param betas instance difficulty given by CGlad model. 
*  @param logLikelihood logLikelihood of the final estimation of the model.  
*  @author enrique.grodrigo
*  @version 0.2.1 
*/
class CGladModel(mu: Dataset[BinarySoftLabel], 
                          prec: Array[Double], 
                          diffic: Array[Double], 
                          clusters: DataFrame
                          ) extends Model[BinarySoftLabel] {
                            
  /**
  *  Method that returns the probabilistic estimation of the true label 
  *
  *  @return org.apache.spark.sql.Dataset
  *  @author enrique.grodrigo
  *  @version 0.1 
  */
  def getMu(): Dataset[BinarySoftLabel] = mu 

  /**
  *  Method that returns the annotator precision information 
  *
  *  @return Double 
  *  @author enrique.grodrigo
  *  @version 0.1 
  */
  def getAnnotatorPrecision(): Array[Double] = prec 

  /**
  *  Method that returns information about instance difficulty
  *
  *  @return Double 
  *  @author enrique.grodrigo
  *  @version 0.1 
  */
  def getInstanceDifficulty(): Array[Double] = diffic 

  def getInstanceCluster(): DataFrame = clusters 

}
