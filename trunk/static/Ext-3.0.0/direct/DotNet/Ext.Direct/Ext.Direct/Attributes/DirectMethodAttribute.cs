using System;

namespace Ext.Direct
{
    /// <summary>
    /// This attribute should be added to methods that will be Ext.direct methods.
    /// </summary>
    [AttributeUsage(AttributeTargets.Method)]
    public class DirectMethodAttribute : Attribute
    {
    }
}
