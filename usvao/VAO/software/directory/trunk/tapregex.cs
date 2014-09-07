// ------------------------------------------------------------------------------
//  <auto-generated>
//    Generated by Xsd2Code. Version 3.4.0.32989
//    <NameSpace>registry</NameSpace><Collection>List</Collection><codeType>CSharp</codeType><EnableDataBinding>False</EnableDataBinding><EnableLazyLoading>False</EnableLazyLoading><TrackingChangesEnable>False</TrackingChangesEnable><GenTrackingClasses>False</GenTrackingClasses><HidePrivateFieldInIDE>False</HidePrivateFieldInIDE><EnableSummaryComment>False</EnableSummaryComment><VirtualProp>False</VirtualProp><IncludeSerializeMethod>False</IncludeSerializeMethod><UseBaseClass>False</UseBaseClass><GenBaseClass>False</GenBaseClass><GenerateCloneMethod>False</GenerateCloneMethod><GenerateDataContracts>False</GenerateDataContracts><CodeBaseTag>Net40</CodeBaseTag><SerializeMethodName>Serialize</SerializeMethodName><DeserializeMethodName>Deserialize</DeserializeMethodName><SaveToFileMethodName>SaveToFile</SaveToFileMethodName><LoadFromFileMethodName>LoadFromFile</LoadFromFileMethodName><GenerateXMLAttributes>False</GenerateXMLAttributes><OrderXMLAttrib>False</OrderXMLAttrib><EnableEncoding>False</EnableEncoding><AutomaticProperties>False</AutomaticProperties><GenerateShouldSerialize>False</GenerateShouldSerialize><DisableDebug>False</DisableDebug><PropNameSpecified>Default</PropNameSpecified><Encoder>UTF8</Encoder><CustomUsings></CustomUsings><ExcludeIncludedTypes>False</ExcludeIncludedTypes><EnableInitializeFields>True</EnableInitializeFields>
//  </auto-generated>
// ------------------------------------------------------------------------------
namespace ivoa.net.ri1_0.server 
{
    using System;
    using System.Diagnostics;
    using System.Xml.Serialization;
    using System.Collections;
    using System.Xml.Schema;
    using System.ComponentModel;
    using System.Collections.Generic;

    //[System.CodeDom.Compiler.GeneratedCodeAttribute("wsdl", "4.0.30319.1")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(TableAccess))]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://www.ivoa.net/xml/TAPRegExt/v1.0")]
    public abstract partial class TAPCapRestriction : Capability
    {
    }

    public partial class UploadMethod
    {
    }
 
    public partial class DataLimit
    {

        private DataLimitUnit unitField;

        private string valueField;

        public DataLimitUnit unit
        {
            get
            {
                return this.unitField;
            }
            set
            {
                this.unitField = value;
            }
        }

        [System.Xml.Serialization.XmlTextAttribute(DataType = "integer")]
        public string Value
        {
            get
            {
                return this.valueField;
            }
            set
            {
                this.valueField = value;
            }
        }
    }

    public enum DataLimitUnit
    {

        /// <remarks/>
        @byte,

        /// <remarks/>
        row,
    }

    public partial class DataLimits
    {

        private DataLimit defaultField;

        private DataLimit hardField;

        public DataLimits()
        {
            this.hardField = new DataLimit();
            this.defaultField = new DataLimit();
        }

        public DataLimit @default
        {
            get
            {
                return this.defaultField;
            }
            set
            {
                this.defaultField = value;
            }
        }

        public DataLimit hard
        {
            get
            {
                return this.hardField;
            }
            set
            {
                this.hardField = value;
            }
        }
    }

    public partial class TimeLimits
    {

        private string defaultField;

        private string hardField;

        public string @default
        {
            get
            {
                return this.defaultField;
            }
            set
            {
                this.defaultField = value;
            }
        }

        public string hard
        {
            get
            {
                return this.hardField;
            }
            set
            {
                this.hardField = value;
            }
        }
    }

    public partial class OutputFormat
    {

        private string mimeField;

        private List<string> aliasField;

        private string ivoidField;

        public OutputFormat()
        {
            this.aliasField = new List<string>();
        }

        public string mime
        {
            get
            {
                return this.mimeField;
            }
            set
            {
                this.mimeField = value;
            }
        }

        public List<string> alias
        {
            get
            {
                return this.aliasField;
            }
            set
            {
                this.aliasField = value;
            }
        }

        public string ivoid
        {
            get
            {
                return this.ivoidField;
            }
            set
            {
                this.ivoidField = value;
            }
        }
    }

    public partial class LanguageFeature
    {

        private string formField;

        private string descriptionField;

        public string form
        {
            get
            {
                return this.formField;
            }
            set
            {
                this.formField = value;
            }
        }

        public string description
        {
            get
            {
                return this.descriptionField;
            }
            set
            {
                this.descriptionField = value;
            }
        }
    }

    public partial class LanguageFeatureList
    {

        private List<LanguageFeature> featureField;

        private string typeField;

        public LanguageFeatureList()
        {
            this.featureField = new List<LanguageFeature>();
        }

        public List<LanguageFeature> feature
        {
            get
            {
                return this.featureField;
            }
            set
            {
                this.featureField = value;
            }
        }

        public string type
        {
            get
            {
                return this.typeField;
            }
            set
            {
                this.typeField = value;
            }
        }
    }

    public partial class Version
    {

        private string ivoidField;

        private string valueField;

        public string ivoid
        {
            get
            {
                return this.ivoidField;
            }
            set
            {
                this.ivoidField = value;
            }
        }

        [System.Xml.Serialization.XmlTextAttribute(DataType = "token")]
        public string Value
        {
            get
            {
                return this.valueField;
            }
            set
            {
                this.valueField = value;
            }
        }
    }

    public partial class Language
    {

        private string nameField;

        private List<Version> versionField;

        private string descriptionField;

        private List<LanguageFeatureList> languageFeaturesField;

        public Language()
        {
            this.languageFeaturesField = new List<LanguageFeatureList>();
            this.versionField = new List<Version>();
        }

        public string name
        {
            get
            {
                return this.nameField;
            }
            set
            {
                this.nameField = value;
            }
        }

        public List<Version> version
        {
            get
            {
                return this.versionField;
            }
            set
            {
                this.versionField = value;
            }
        }

        public string description
        {
            get
            {
                return this.descriptionField;
            }
            set
            {
                this.descriptionField = value;
            }
        }

        public List<LanguageFeatureList> languageFeatures
        {
            get
            {
                return this.languageFeaturesField;
            }
            set
            {
                this.languageFeaturesField = value;
            }
        }
    }

    public partial class DataModelType
    {

        private string ivoidField;

        private string valueField;

        public string ivoid
        {
            get
            {
                return this.ivoidField;
            }
            set
            {
                this.ivoidField = value;
            }
        }

        [System.Xml.Serialization.XmlTextAttribute(DataType = "token")]
        public string Value
        {
            get
            {
                return this.valueField;
            }
            set
            {
                this.valueField = value;
            }
        }
    }


    //[System.CodeDom.Compiler.GeneratedCodeAttribute("wsdl", "4.0.30319.1")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://www.ivoa.net/xml/TAPRegExt/v1.0")]
    public partial class TableAccess : TAPCapRestriction
    {

        private List<DataModelType> dataModelField;

        private List<Language> languageField;

        private List<OutputFormat> outputFormatField;

        private List<UploadMethod> uploadMethodField;

        private TimeLimits retentionPeriodField;

        private TimeLimits executionDurationField;

        private DataLimits outputLimitField;

        private DataLimits uploadLimitField;

        public TableAccess()
        {
            this.uploadLimitField = new DataLimits();
            this.outputLimitField = new DataLimits();
            this.executionDurationField = new TimeLimits();
            this.retentionPeriodField = new TimeLimits();
            this.uploadMethodField = new List<UploadMethod>();
            this.outputFormatField = new List<OutputFormat>();
            this.languageField = new List<Language>();
            this.dataModelField = new List<DataModelType>();
        }

        public List<DataModelType> dataModel
        {
            get
            {
                return this.dataModelField;
            }
            set
            {
                this.dataModelField = value;
            }
        }

        public List<Language> language
        {
            get
            {
                return this.languageField;
            }
            set
            {
                this.languageField = value;
            }
        }

        public List<OutputFormat> outputFormat
        {
            get
            {
                return this.outputFormatField;
            }
            set
            {
                this.outputFormatField = value;
            }
        }

        public List<UploadMethod> uploadMethod
        {
            get
            {
                return this.uploadMethodField;
            }
            set
            {
                this.uploadMethodField = value;
            }
        }

        public TimeLimits retentionPeriod
        {
            get
            {
                return this.retentionPeriodField;
            }
            set
            {
                this.retentionPeriodField = value;
            }
        }

        public TimeLimits executionDuration
        {
            get
            {
                return this.executionDurationField;
            }
            set
            {
                this.executionDurationField = value;
            }
        }

        public DataLimits outputLimit
        {
            get
            {
                return this.outputLimitField;
            }
            set
            {
                this.outputLimitField = value;
            }
        }

        public DataLimits uploadLimit
        {
            get
            {
                return this.uploadLimitField;
            }
            set
            {
                this.uploadLimitField = value;
            }
        }
    }
}
