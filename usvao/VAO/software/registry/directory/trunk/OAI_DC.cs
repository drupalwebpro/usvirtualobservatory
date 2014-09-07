﻿using System;
using System.Data;

using ivoa.net.vr1_0;
using ivoa.net.ri1_0;
using oai_dc;
using oai;

namespace registry
{
     class OAI_DC
    {
        public static oai_dc.oai_dcType CreateOAIDC(DataRow dr)
        {

            oai_dc.oai_dcType odt = new oai_dc.oai_dcType();

            //odt.ItemsElementName = new ItemsChoiceType[6];
            odt.ItemsElementName = new ItemsChoiceType[4];

            odt.ItemsElementName[0] = ItemsChoiceType.title;
            odt.ItemsElementName[1] = ItemsChoiceType.description;
            odt.ItemsElementName[2] = ItemsChoiceType.identifier;
            //odt.ItemsElementName[3] = ItemsChoiceType.publisher;
            //odt.ItemsElementName[4] = ItemsChoiceType.subject;
            odt.ItemsElementName[3] = ItemsChoiceType.date;

            odt.Items = new elementType[4];

            int ind = 0;

            odt.Items[ind++] = new elementType();
            odt.Items[0].Value = (string)dr["res_title"];
            odt.Items[ind++] = new elementType();
            odt.Items[1].Value = (string)dr["res_description"];
            odt.Items[ind++] = new elementType();
            odt.Items[2].Value = (string)dr["ivoid"];
            odt.Items[ind++] = new elementType();
            //odt.Items[3].Value = (string)dr["publisher"];
            //odt.Items[ind++] = new elementType();
            //odt.Items[4].Value = (string)dr["subject"];
            //odt.Items[ind++] = new elementType();
            odt.Items[3].Value = GetOAIDatestamp((DateTime)dr["updated"], oai.granularityType.YYYYMMDDThhmmssZ);

            return odt;
        }


        private static string buf2(int inpn)
        {
            string inp = "" + inpn;
            if (inp.Length < 2)
                return "0" + inp;
            else return inp;
        }
        public static String GetOAIDatestamp(DateTime date, oai.granularityType granularity)
        {
            string datestring = date.Year.ToString() + "-" + buf2(date.Month) + "-" + buf2(date.Day);
            if (granularity == granularityType.YYYYMMDDThhmmssZ)
                datestring += "T" + buf2(date.Hour) + ":" + buf2(date.Minute) + ":" + buf2(date.Second) + "Z";

            return datestring;
        }
    }
}