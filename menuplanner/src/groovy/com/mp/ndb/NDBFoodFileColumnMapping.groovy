package com.mp.ndb
/**
 * Created on Apr 10, 2011
 * Created by Brent Fisher
 *
 * This file contains proprietary information of COMPANY
 * Copying or reproduction without prior written approval is prohibited.
 * (c) Copyright 1998-2011 COMPANY
 * All rights reserved.
 */
 
public class NDBFoodFileColumnMapping {
   static final foodColumnMappingString = """0	ndbNo
1	shrtDesc
2	water
3	energKcal
4	protein
5	lipidTot
6	ash
7	carbohydrt
8	fiberTD
9	sugarTot
10	calcium
11	iron
12	magnesium
13	phosphorus
14	potassium
15	sodium
16	zinc
17	copper
18	manganese
19	selenium
20	vitC
21	thiamin
22	riboflavin
23	niacin
24	pantoAcid
25	vitB6
26	folateTot
27	folicAcid
28	foodFolate
29	folateDFE
30	cholineTot
31	vitB12
32	vitA_IU
33	vitA_RAE
34	retinol
35	alphaCarot
36	betaCarot
37	betaCrypt
38	lycopene
39	lutZea
40	vitE
41	vitD_mcg
42	vitD_IU
43	vitK
44	faSat
45	faMono
46	faPoly
47	cholestrl
48	gmWt1
49	gmWtDesc1
50	gmWt2
51	gmWtDesc2
52	refusePct"""

  def static weightMappingString = """0	ndbNo
1	seq
2	amount
3	msreDesc
4	gmWgt
5	numDataPts
6	stdDev"""
  public static weightColumnMapping() {
    def retVal = [:]
    weightMappingString.eachLine {
      def line = it.split("\t");
      retVal ["${line[0]}"] =  "${line[1]}"
    }
    retVal
  }
  public static foodColumnMapping() {
    def retVal = [:]
    foodColumnMappingString.eachLine {
      def line = it.split("\t");
      retVal ["${line[0]}"] =  "${line[1]}"
    }
    retVal
  }
}