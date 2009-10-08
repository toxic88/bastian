using System;

namespace Ext.Direct
{
    /// <summary>
    /// This attribute should be added to Ext.direct methods that will be used
    /// to save forms.
    /// </summary>
    [AttributeUsage(AttributeTargets.Method)]
    public class DirectMethodFormAttribute : DirectMethodAttribute
    {
    }
}
