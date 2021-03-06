/*
 * KeywordFactory.java
 * $ID*
 */

package dalserver;

import java.io.*;
import java.util.*;
import cds.savot.model.*;

/**
 * KeywordFactory implements a factory class for well-known DAL keywords,
 * including Groups, Params and Fields.  The use of a factory class frees
 * the client from having to know all the detailed metadata associated
 * with each type of keyword.  Keywords are indexed by both their ID and
 * UTYPE tags.  In general UTYPE is required to ensure uniqueness, but
 * within a limited scope, the ID tag may be sufficient to uniquely identify
 * a keyword without having to know the full UTYPE.
 *
 * @version	1.0, 13-Dec-2006
 * @author	Doug Tody
 */
public class KeywordFactory implements Iterable {
    /** The keyword hash table. */
    private LinkedHashMap<String,Object> map;

    // Internal data (need to generalize the following eventually).
    private String optPrefix = "";

    /** Null constructor to generate a new keyword factory. */
    public KeywordFactory() {
	this.map = new LinkedHashMap<String,Object>();
    }

    /**
     * Generate a new model-specific keyword factory.
     *
     * @param	prefix	An optional prefix permitted when doing keyword
     *			lookups on UTYPE values.  That is, a key will be
     *			recognized whether or not this prefix is present.
     *			The prefix string should include the "." delimiter.
     */
    public KeywordFactory(String prefix) {
	this.map = new LinkedHashMap<String,Object>();
	this.optPrefix = prefix;
    }

    /**
     * Add a new Group keyword to the dictionary.
     *
     * @param	name	The default name for the parameter, or null.
     * @param	id	The ID by which the keyword is known locally.
     * @param	gid	The Group ID identifying the Group.
     * @param	utype	The UTYPE tag (data model field identifier).
     * @param	ucd	The UCD tag of the group, if applicable.
     * @param	descr	A short string describing the Group.
     * @param	hint	Hint codes for a group element.
     */
    public void addGroup(String name, String id, String gid,
	String utype, String ucd, String descr, String hint) {

	TableGroup g = new TableGroup(name, id, gid, utype, ucd, descr);
	g.setHint(hint);

	map.put(id, (Object)g);		// indexed by ID
	map.put(utype, (Object)g);	// indexed by UTYPE
    }

    /**
     * Add a new Param (or Field) keyword to the dictionary.
     *
     * @param	name	The default name for the parameter, or null.
     * @param	value	The default value for the parameter, or null.
     * @param	id	The ID by which the keyword is known locally.
     * @param	gid	The ID of the group to which the keyword belongs.
     * @param	type	The VOTable-style datatype of the keyword.
     * @param	size	The number of elements, for array-valued keywords.
     * @param	unit	The units of the keyword value, as a string.
     * @param	utype	The UTYPE tag (data model field identifier).
     * @param	ucd	The UCD tag (semantic type of the keyword value).
     * @param	descr	A short string describing the keyword.
     * @param 	fits	The FITS keyword equivalent, if any.
     * @param 	csv	The CSV mapping and lable, if any.
     * @param	hint	Set to "p" if the field defaults to a param.
     */
    public void addParam(String name, String value, String id, String gid,
	String type, String size, String unit, String utype, String ucd,
	String descr, String fits, String csv, String hint) {

	TableParam p = new TableParam(name, value, id, gid, type, size, unit,
	    utype, ucd, descr);

	p.setHint(hint);
	p.setFitsKeyword(fits);
	p.setCsvKeyword(csv);

	map.put(id, (Object)p);		// indexed by ID
	map.put(utype, (Object)p);	// indexed by UTYPE
    }

    /**
     * Generate a new copy of a well-known GROUP keyword.
     *
     * @param	key	Key used to lookup keyword (ID or UTYPE).
     *
     * @return		Returns a new TableGroup object with the default
     *			attributes for the well-known keyword filled in.
     *			Use the GroupID key defined for the group to identify
     *			other groups, fields, or params as members of the
     *			group.
     */
    public TableGroup newGroup(String key) throws DalServerException {

	return (this.newGroup(key, null));
    }

    /**
     * Generate a new copy of a well-known PARAM keyword.
     *
     * @param	key	Key used to lookup keyword (ID or UTYPE).
     * @param	value	Parameter value to be set.
     *
     * @return		Returns a new TableParam object with the default
     *			attributes of the well-known keyword filled in.
     *			If the Param has a default value this will be set
     *			as well.
     */
    public TableParam newParam(String key, String value)
	throws DalServerException {

	return (this.newParam(key, null, value));
    }

    /**
     * Generate a new copy of a well-known PARAM keyword.
     *
     * @param	key	Key used to lookup keyword (ID or UTYPE).
     * @param	value	Parameter value to be set.
     */
    public TableParam newParam(String key, int value)
	throws DalServerException {

	return (this.newParam(key, new Integer(value).toString()));
    }

    /**
     * Generate a new copy of a well-known PARAM keyword.
     *
     * @param	key	Key used to lookup keyword (ID or UTYPE).
     * @param	value	Parameter value to be set.
     */
    public TableParam newParam(String key, double value)
	throws DalServerException {

	return (this.newParam(key, new Double(value).toString()));
    }

    /**
     * Generate a new copy of a well-known FIELD keyword.
     *
     * @param	key	Key used to lookup keyword (ID or UTYPE).
     *
     * @return		Returns a new TableField object with the default
     *			attributes of the well-known keyword filled in.
     */
    public TableField newField(String key) throws DalServerException {
	return (this.newField(key, null));
    }

    /**
     * Generate a new copy of a well-known GROUP keyword, with a unique
     * group ID.
     *
     * @param	key	Key used to lookup keyword (ID or UTYPE).
     * @param	groupId	Unique group ID to be used, instead of default.
     *
     * @return		Returns a new TableGroup object with the default
     *			attributes for the well-known keyword filled in.
     *			Use the GroupID key defined for the group to identify
     *			other groups, fields, or params as members of the
     *			group.
     */
    public TableGroup newGroup(String key, String groupId)
	    throws DalServerException {

	String keyword = key;

	// Is this is a standard keyword?
	if (!map.containsKey(keyword=key))
	    if (!map.containsKey(keyword = optPrefix + key))
		throw new DalServerException(
		    "Cannot find keyword " + "["+key+"]");

	// Is it a Group keyword?
	Object o = map.get(keyword);
	if (!(o instanceof TableGroup))
	    throw new DalServerException ("Not a Group keyword " + "["+key+"]");

	TableGroup g = (TableGroup) o;
	TableGroup group = new TableGroup(g.getName(), g.getId(),
	    groupId == null ? g.getGroupId() : groupId,
	    g.getUtype(), g.getUcd(), g.getDescription());

	group.setHint(g.getHint());

	return (group);
    }

    /**
     * Generate a new copy of a well-known PARAM keyword, using the given
     * group ID.
     *
     * @param	key	Key used to lookup keyword (ID or UTYPE).
     * @param	groupId	Group ID of the group to which the field belongs.
     * @param	value	Parameter value to be set.
     *
     * @return		Returns a new TableParam object with the default
     *			attributes of the well-known keyword filled in.
     *			If the Param has a default value this will be set
     *			as well.
     */
    public TableParam newParam(String key, String groupId, String value)
	throws DalServerException {

	String keyword = key;

	// Is this is a standard keyword?
	if (!map.containsKey(keyword=key))
	    if (!map.containsKey(keyword = optPrefix + key))
		throw new DalServerException(
		    "Cannot find keyword "+"["+key+"]");

	// Is it a Param keyword?
	Object o = map.get(keyword);
	if (!(o instanceof TableParam))
	    throw new DalServerException ("Not a Param keyword " + "["+key+"]");

	TableParam p = (TableParam) o;
	TableParam param = new TableParam(p.getName(),
	    (value == null) ? p.getValue() : value, p.getId(),
	    groupId == null ? p.getGroupId() : groupId,
	    p.getDataType(), p.getArraySize(), p.getUnit(),
	    p.getUtype(), p.getUcd(), p.getDescription());

	param.setFitsKeyword(p.getFitsKeyword());
	param.setCsvKeyword(p.getCsvKeyword());
	param.setHint(p.getHint());

	return (param);
    }

    /**
     * Generate a new copy of a well-known FIELD keyword, using the given
     * group ID.
     *
     * @param	key	Key used to lookup keyword (ID or UTYPE).
     * @param	groupId	Group ID of the group to which the field belongs.
     *
     * @return		Returns a new TableField object with the default
     *			attributes of the well-known keyword filled in.
     */
    public TableField newField(String key, String groupId)
	    throws DalServerException {

	String keyword = key;

	// Is this is a standard keyword?
	if (!map.containsKey(keyword=key))
	    if (!map.containsKey(keyword = optPrefix + key))
		throw new DalServerException(
		    "Cannot find keyword "+"["+key+"]");

	// Is it a Param keyword? (Fields are stored as Params)
	Object o = map.get(keyword);
	if (!(o instanceof TableParam))
	    throw new DalServerException ("Not a Field keyword " + "["+key+"]");

	TableParam p = (TableParam) o;
	TableField field = new TableField(p.getName(), p.getId(),
	    groupId == null ? p.getGroupId() : groupId,
	    p.getDataType(), p.getArraySize(), p.getUnit(),
	    p.getUtype(), p.getUcd(), p.getDescription());

	field.setFitsKeyword(p.getFitsKeyword());
	field.setCsvKeyword(p.getCsvKeyword());
	field.setHint(p.getHint());

	return (field);
    }

    /** Get a list iterator for the keyword list. */
    public Iterator iterator() {
	return (map.entrySet().iterator());
    }

    /** Get an entrySet to access the keyword list as a Collection. */
    public Set entrySet() {
	return (map.entrySet());
    }

    /**
     * Generate an HTML version of a keyword dictionary.  The generated
     * file should be copied to the dalserver/doc-files directory to have
     * it included in the javadoc.
     *
     * @param	fname		The name of the output file to be created.
     * @param	title		The title for the HTML page.
     * @param	hint		Print only keywords which match the given
     *				"hint" value.  A null value means print
     *				everything.
     */
    public void printDoc(String fname, String title, String hint) {
	Object last=null, obj=null;

	try {
	    // Output the keyword dictionary in HTML format.
	    PrintWriter out = new PrintWriter(new FileWriter(fname));
	    out.println("<HTML><HEAD>");
	    out.println("<TITLE>" + title + "</TITLE>");
	    out.println("</HEAD><BODY>");
	    out.println("<TABLE width=950 align=center>");

	    out.println("<TR><TD colspan=5 bgcolor=\"LightGray\" " +
		"align=center><B>" + title + "</B></TD></TR>");
	    out.println("<TR>" +
		"<TD align=left><I>ID</I></TD>" +
		"<TD align=left><I>UTYPE</I></TD>" +
		"<TD align=left><I>DataType</I></TD>" +
		"<TD align=left><I>Unit</I></TD>" +
		"<TD align=left><I>Description</I></TD>" +
		"</TR>");

	    for (Iterator i = this.iterator();  i.hasNext();  last=obj) {
		Map.Entry me = (Map.Entry) i.next();
		obj = (Object) me.getValue();
		if (obj == last)
		    continue;
		
		if (obj instanceof TableGroup) {
		    TableGroup o = (TableGroup)obj;
		    if (o.hintContains("h"))
			continue;

		    if (last != null)
			out.println("<TR><TD colspan=5></TD></TR>");
		    out.println("<TR><TD colspan=5 bgcolor=\"LightGray\" " +
			"align=center><I>" + o.getDescription() +
			"</I></TD></TR>");

		    out.println("<TR><TD>" + o.getId() + 
			"</TD><TD>" + o.getUtype() + 
			"</TD><TD width=60 align=\"center\">" + "GROUP" + 
			"</TD><TD width=50 align=\"left\">" + "" + 
			"</TD><TD>" + o.getDescription() + 
			"</TD></TR>");

		} else if (obj instanceof TableParam) {
		    TableParam o = (TableParam)obj;
		    if (o.hintContains("h"))
			continue;

		    if (hint == null || o.hintContains(hint)) {
			out.println("<TR><TD>" + o.getId() + 
			    "</TD><TD>" + o.getUtype() + 
			    "</TD><TD align=\"center\">" + o.getDataType() + 
			    "</TD><TD align=\"left\">" + o.getUnit() + 
			    "</TD><TD>" + o.getDescription() + 
			    "</TD></TR>");
		    }
		}
	    }

	    out.println("</TABLE>");
	    out.println("</BODY></HTML>");
	    out.close();

	} catch (IOException ex) {
	    System.out.println ("cannot write file " + fname);
	}
    }


    /**
     * Generate Java code which can be included in a class to add all
     * keyword factory keywords to a RequestResponse object.
     *
     * @param	fname		The name of the output file to be created.
     *
     * @param	factory		Handle to be used for the keyword factory.
     *
     * @param	hint		Only generate code for parameters which
     *				match the given hint value.  A null value
     *				means print everything.
     */
    public void printCode(String fname, String factory, String hint) {
	Object last=null, obj=null;

	try {
	    // Output Java code for generating the  keyword dictionary.
	    PrintWriter out = new PrintWriter(new FileWriter(fname));

	    for (Iterator i = this.iterator();  i.hasNext();  last=obj) {
		Map.Entry me = (Map.Entry) i.next();
		obj = (Object) me.getValue();
		if (obj == last)
		    continue;
		
		if (obj instanceof TableGroup) {
		    TableGroup o = (TableGroup)obj;
		    if (o.hintContains("h"))
			continue;
		    if (last != null)
			out.println("");

		    out.println("// " + o.getDescription());
		    out.println("r.addGroup(" + factory + ".newGroup(\"" +
			o.getId() + "\"));");

		} else if (obj instanceof TableParam) {
		    TableParam o = (TableParam)obj;
		    if (o.hintContains("h"))
			continue;

		    if (hint == null || o.hintContains(hint)) {
			if (o.hintContains("p")) {
			    String defval = o.getValue();
			    if (defval == null || defval.length() == 0)
				defval = "UNSET";

			    out.println("r.addParam(" + factory +
				".newParam(\"" + o.getId() +
				"\"" + ", \"" + defval + "\"));");

			} else {
			    out.println("r.addField(" + factory +
				".newField(\"" + o.getId() + "\"));");
			}
		    }
		}
	    }

	    out.close();

	} catch (IOException ex) {
	    System.out.println ("cannot write file " + fname);
	}
    }


    /**
     * Utility to convert a CSV version of a data model in to a keyword
     * dictionary.
     */
    public static void main (String[] args) {
	SsapKeywordFactory keywords = new SsapKeywordFactory();
	if (args.length == 0 || args[0].equals("doc")) {
	}
    }
}

