/*************************************************************************

   Copyright (c) 2014 California Institute of Technology, Pasadena,
   California.    Based on Cooperative Agreement Number NCC5-626 between
   NASA and the California Institute of Technology. All rights reserved.

   Redistribution and use in source and binary forms, with or without
   modification, are permitted provided that the following conditions
   of this BSD 3-clause license are met:

   1. Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.

   2. Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the distribution.

   3. Neither the name of the copyright holder nor the names of its
   contributors may be used to endorse or promote products derived from
   this software without specific prior written permission.

   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
   "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
   LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
   A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
   HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
   SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
   LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
   DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
   THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
   OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

   This software was developed at the Infrared Processing and Analysis
   Center (IPAC) and the Jet Propulsion Laboratory (JPL) by Bruce
   Berriman, John Good, Joseph Jacob, Daniel S. Katz, and Anastasia
   Laity.

*************************************************************************/



#include <stdio.h>
#include <math.h>
#include <pixbounds.h>

main()
{
   int i;
   double x[1000], y[1000];

   i = 0;
   while ( (i < 1000) && (scanf("%lf %lf",&x[i], &y[i]) != EOF) ) 
      ++i;
   
   /* geomSetDebug(); */

   cgeomInit(x, y, i);

   printf("\nCenter:    (%-g, %-g)\n",
      cgeomGetXcen(), 
      cgeomGetYcen());

   printf("Size:      %-g x %-g\n",
      cgeomGetWidth(), 
      cgeomGetHeight());

   printf("Rotation:  %-g\n\n",
      cgeomGetAngle()); 

   exit(0);
}