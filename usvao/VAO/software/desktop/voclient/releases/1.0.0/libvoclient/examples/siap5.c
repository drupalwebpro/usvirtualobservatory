/************************************************************************
 *   Call a SIAP search service and print a summary selected fields of the
 *   results.
 *
 *   Usage:        siap5 ra dec size [serviceURL]
 *
 *   Or call with no args for the built-in unit test.
 *
 *  M. Fitzpatrick, NOAO, Feb 2009
 */


#include <stdio.h>
#include <stdlib.h>
#include "VOClient.h"

double  ra       = 12.0;			/* default values	*/
double  dec      = 0.0;
double  size     = 0.2;

char   *service  = "http://skyview.gsfc.nasa.gov/cgi-bin/vo/sia.pl?";
char   *server   = "6200:localhost";

static void callSiapService (char *url, double ra, double dec, double size);



int main (int argc, char *argv[])
{
    /* Process command line arguments.
     */
    if (argc <= 1) {
	/* Use builtin defaults. */
    } else if (argc >= 3) {
	int arg = 1;
    
	/* Look for a server specification. */
	if (strncmp (argv[arg], "-ds",3) == 0)
	    server = argv[++arg];
	ra   = atof (argv[arg++]);
	dec  = atof (argv[arg++]);
	size = atof (argv[arg++]);
	if (arg < argc)
	    service = argv[arg++];

    } else {
        fprintf (stderr, "Usage: siap1 [-ds server] ra dec size [siapURL]\n");
        exit(1);
    }

    /* Now call the SIAP Service and summarize the results.
     */
    callSiapService (service, ra, dec, size);

    return (0);
}


/* Simple test routine to call a SIA service and summarize results.
 */
static void
callSiapService (char *service_url, double ra, double dec, double size)
{
    char *voc_opts = NULL;
    int  i=0, nrec=0, nattr=0;
    char *attrList = NULL, *ip;

    DAL	      siap;				/* DAL Connection	 */
    Query     query;				/* query handle		 */
    QResponse qr;				/* query response handle */
    QRecord   rec;				/* result record andle   */
	

    /*  Initialize the VOClient code.  Error messages are printed by the
     *  interface so we just quit if there is a problem.
     */
    if (voc_initVOClient (voc_opts) == ERR) 
        return;

    /*  Get a new connection to the named service.
     */
    siap = voc_openSiapConnection (service_url);    /* open a connection    */

    /*  Form a query.  Here we'll use the one search size we're given for
     *  both the RA,DEC sizes, and specify a null format.
     */
    query = voc_getSiapQuery (siap, ra, dec, size, size, NULL);

    printf ("Executing Query:\n  %s\n\n", 
	voc_getQueryString (query, SIAP_CONN, 0));
    qr = voc_executeQuery (query); 		    /* execute the query    */


    if ((nrec = voc_getRecordCount (qr)) <= 0) {    /* summarize response   */
        fprintf (stderr, "no records matched");
        exit (1);

    } else {
        rec = voc_getRecord (qr, 0);
        nattr = (rec != ERR) ? voc_getAttrCount (rec) : 0;


	printf ("# returns %d records containing %d attributes each\n#\n",
	    nrec, nattr);
        printf ("# --- Summary output ---\n#\n");

	attrList = voc_getAttrList (rec);
        printf ("# Attribute List:\n#    ");
	for (ip = attrList; *ip; ip++)
	    if (isspace (*ip))
		printf ("\n#    ");
	    else
		putchar (*ip);
        printf ("\n#\n");


	printf ("\n\n");
	printf ("#N,ID,UCD,Name,UType\n");
	for (i=0; i < nattr; i++) {
	    printf ("%d,%s,%s,%s,%s\n", i, 
                voc_getFieldAttr (qr, i, "id"),
                voc_getFieldAttr (qr, i, "ucd"),
                voc_getFieldAttr (qr, i, "name"),
                voc_getFieldAttr (qr, i, "utype"));
	}
    }

    voc_closeConnection (siap);			/* close the siap connection */
    voc_closeVOClient (1);		        /* clean up and shutdown     */
    free ((void *) attrList);

    return;
}
