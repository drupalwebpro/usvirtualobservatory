# This file was automatically generated by SWIG (http://www.swig.org).
# Version 2.0.10
#
# Do not make changes to this file unless you know what you are doing--modify
# the SWIG interface file instead.



from sys import version_info
if version_info >= (2,6,0):
    def swig_import_helper():
        from os.path import dirname
        import imp
        fp = None
        try:
            fp, pathname, description = imp.find_module('_VOClient', [dirname(__file__)])
        except ImportError:
            import _VOClient
            return _VOClient
        if fp is not None:
            try:
                _mod = imp.load_module('_VOClient', fp, pathname, description)
            finally:
                fp.close()
            return _mod
    _VOClient = swig_import_helper()
    del swig_import_helper
else:
    import _VOClient
del version_info
try:
    _swig_property = property
except NameError:
    pass # Python < 2.2 doesn't have 'property'.
def _swig_setattr_nondynamic(self,class_type,name,value,static=1):
    if (name == "thisown"): return self.this.own(value)
    if (name == "this"):
        if type(value).__name__ == 'SwigPyObject':
            self.__dict__[name] = value
            return
    method = class_type.__swig_setmethods__.get(name,None)
    if method: return method(self,value)
    if (not static):
        self.__dict__[name] = value
    else:
        raise AttributeError("You cannot add attributes to %s" % self)

def _swig_setattr(self,class_type,name,value):
    return _swig_setattr_nondynamic(self,class_type,name,value,0)

def _swig_getattr(self,class_type,name):
    if (name == "thisown"): return self.this.own()
    method = class_type.__swig_getmethods__.get(name,None)
    if method: return method(self)
    raise AttributeError(name)

def _swig_repr(self):
    try: strthis = "proxy of " + self.this.__repr__()
    except: strthis = ""
    return "<%s.%s; %s >" % (self.__class__.__module__, self.__class__.__name__, strthis,)

try:
    _object = object
    _newclass = 1
except AttributeError:
    class _object : pass
    _newclass = 0



def coneCaller(*args):
  return _VOClient.coneCaller(*args)
coneCaller = _VOClient.coneCaller

def coneCallerToFile(*args):
  return _VOClient.coneCallerToFile(*args)
coneCallerToFile = _VOClient.coneCallerToFile

def siapCaller(*args):
  return _VOClient.siapCaller(*args)
siapCaller = _VOClient.siapCaller

def siapCallerToFile(*args):
  return _VOClient.siapCallerToFile(*args)
siapCallerToFile = _VOClient.siapCallerToFile

def ssapCaller(*args):
  return _VOClient.ssapCaller(*args)
ssapCaller = _VOClient.ssapCaller

def ssapCallerToFile(*args):
  return _VOClient.ssapCallerToFile(*args)
ssapCallerToFile = _VOClient.ssapCallerToFile

def getRawURL(*args):
  return _VOClient.getRawURL(*args)
getRawURL = _VOClient.getRawURL

def validateObject(*args):
  return _VOClient.validateObject(*args)
validateObject = _VOClient.validateObject

def initVOClient(*args):
  return _VOClient.initVOClient(*args)
initVOClient = _VOClient.initVOClient

def closeVOClient(*args):
  return _VOClient.closeVOClient(*args)
closeVOClient = _VOClient.closeVOClient

def abortVOClient(*args):
  return _VOClient.abortVOClient(*args)
abortVOClient = _VOClient.abortVOClient

def openConnection(*args):
  return _VOClient.openConnection(*args)
openConnection = _VOClient.openConnection

def openConeConnection(*args):
  return _VOClient.openConeConnection(*args)
openConeConnection = _VOClient.openConeConnection

def openSiapConnection(*args):
  return _VOClient.openSiapConnection(*args)
openSiapConnection = _VOClient.openSiapConnection

def openSsapConnection(*args):
  return _VOClient.openSsapConnection(*args)
openSsapConnection = _VOClient.openSsapConnection

def closeConnection(*args):
  return _VOClient.closeConnection(*args)
closeConnection = _VOClient.closeConnection

def getServiceCount(*args):
  return _VOClient.getServiceCount(*args)
getServiceCount = _VOClient.getServiceCount

def addServiceURL(*args):
  return _VOClient.addServiceURL(*args)
addServiceURL = _VOClient.addServiceURL

def getServiceURL(*args):
  return _VOClient.getServiceURL(*args)
getServiceURL = _VOClient.getServiceURL

def getQuery(*args):
  return _VOClient.getQuery(*args)
getQuery = _VOClient.getQuery

def getConeQuery(*args):
  return _VOClient.getConeQuery(*args)
getConeQuery = _VOClient.getConeQuery

def getSiapQuery(*args):
  return _VOClient.getSiapQuery(*args)
getSiapQuery = _VOClient.getSiapQuery

def getSsapQuery(*args):
  return _VOClient.getSsapQuery(*args)
getSsapQuery = _VOClient.getSsapQuery

def addIntParam(*args):
  return _VOClient.addIntParam(*args)
addIntParam = _VOClient.addIntParam

def addFloatParam(*args):
  return _VOClient.addFloatParam(*args)
addFloatParam = _VOClient.addFloatParam

def addStringParam(*args):
  return _VOClient.addStringParam(*args)
addStringParam = _VOClient.addStringParam

def getQueryString(*args):
  return _VOClient.getQueryString(*args)
getQueryString = _VOClient.getQueryString

def executeQuery(*args):
  return _VOClient.executeQuery(*args)
executeQuery = _VOClient.executeQuery

def executeCSV(*args):
  return _VOClient.executeCSV(*args)
executeCSV = _VOClient.executeCSV

def executeVOTable(*args):
  return _VOClient.executeVOTable(*args)
executeVOTable = _VOClient.executeVOTable

def executeQueryAs(*args):
  return _VOClient.executeQueryAs(*args)
executeQueryAs = _VOClient.executeQueryAs

def getRecordCount(*args):
  return _VOClient.getRecordCount(*args)
getRecordCount = _VOClient.getRecordCount

def getRecord(*args):
  return _VOClient.getRecord(*args)
getRecord = _VOClient.getRecord

def getFieldAttr(*args):
  return _VOClient.getFieldAttr(*args)
getFieldAttr = _VOClient.getFieldAttr

def getAttribute(*args):
  return _VOClient.getAttribute(*args)
getAttribute = _VOClient.getAttribute

def intValue(*args):
  return _VOClient.intValue(*args)
intValue = _VOClient.intValue

def floatValue(*args):
  return _VOClient.floatValue(*args)
floatValue = _VOClient.floatValue

def stringValue(*args):
  return _VOClient.stringValue(*args)
stringValue = _VOClient.stringValue

def getIntAttr(*args):
  return _VOClient.getIntAttr(*args)
getIntAttr = _VOClient.getIntAttr

def getFloatAttr(*args):
  return _VOClient.getFloatAttr(*args)
getFloatAttr = _VOClient.getFloatAttr

def getStringAttr(*args):
  return _VOClient.getStringAttr(*args)
getStringAttr = _VOClient.getStringAttr

def getAttrList(*args):
  return _VOClient.getAttrList(*args)
getAttrList = _VOClient.getAttrList

def getAttrCount(*args):
  return _VOClient.getAttrCount(*args)
getAttrCount = _VOClient.getAttrCount

def getDataset(*args):
  return _VOClient.getDataset(*args)
getDataset = _VOClient.getDataset

def debugLevel(*args):
  return _VOClient.debugLevel(*args)
debugLevel = _VOClient.debugLevel

def regSearch(*args):
  return _VOClient.regSearch(*args)
regSearch = _VOClient.regSearch

def regSearchByService(*args):
  return _VOClient.regSearchByService(*args)
regSearchByService = _VOClient.regSearchByService

def regQuery(*args):
  return _VOClient.regQuery(*args)
regQuery = _VOClient.regQuery

def regAddSearchTerm(*args):
  return _VOClient.regAddSearchTerm(*args)
regAddSearchTerm = _VOClient.regAddSearchTerm

def regRemoveSearchTerm(*args):
  return _VOClient.regRemoveSearchTerm(*args)
regRemoveSearchTerm = _VOClient.regRemoveSearchTerm

def regSortRes(*args):
  return _VOClient.regSortRes(*args)
regSortRes = _VOClient.regSortRes

def regGetSTCount(*args):
  return _VOClient.regGetSTCount(*args)
regGetSTCount = _VOClient.regGetSTCount

def regGetQueryString(*args):
  return _VOClient.regGetQueryString(*args)
regGetQueryString = _VOClient.regGetQueryString

def regExecute(*args):
  return _VOClient.regExecute(*args)
regExecute = _VOClient.regExecute

def regExecuteRaw(*args):
  return _VOClient.regExecuteRaw(*args)
regExecuteRaw = _VOClient.regExecuteRaw

def resGetCount(*args):
  return _VOClient.resGetCount(*args)
resGetCount = _VOClient.resGetCount

def resGetStr(*args):
  return _VOClient.resGetStr(*args)
resGetStr = _VOClient.resGetStr

def resGetFloat(*args):
  return _VOClient.resGetFloat(*args)
resGetFloat = _VOClient.resGetFloat

def resGetInt(*args):
  return _VOClient.resGetInt(*args)
resGetInt = _VOClient.resGetInt

def nameResolver(*args):
  return _VOClient.nameResolver(*args)
nameResolver = _VOClient.nameResolver

def resolverPos(*args):
  return _VOClient.resolverPos(*args)
resolverPos = _VOClient.resolverPos

def resolverRA(*args):
  return _VOClient.resolverRA(*args)
resolverRA = _VOClient.resolverRA

def resolverDEC(*args):
  return _VOClient.resolverDEC(*args)
resolverDEC = _VOClient.resolverDEC

def resolverRAErr(*args):
  return _VOClient.resolverRAErr(*args)
resolverRAErr = _VOClient.resolverRAErr

def resolverDECErr(*args):
  return _VOClient.resolverDECErr(*args)
resolverDECErr = _VOClient.resolverDECErr

def resolverOtype(*args):
  return _VOClient.resolverOtype(*args)
resolverOtype = _VOClient.resolverOtype

def skybot(*args):
  return _VOClient.skybot(*args)
skybot = _VOClient.skybot

def skybotNObjs(*args):
  return _VOClient.skybotNObjs(*args)
skybotNObjs = _VOClient.skybotNObjs

def skybotStrAttr(*args):
  return _VOClient.skybotStrAttr(*args)
skybotStrAttr = _VOClient.skybotStrAttr

def skybotDblAttr(*args):
  return _VOClient.skybotDblAttr(*args)
skybotDblAttr = _VOClient.skybotDblAttr
# This file is compatible with both classic and new-style classes.

