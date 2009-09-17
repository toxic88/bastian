using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Reflection;
using Newtonsoft.Json;

namespace Ext.Direct
{
    internal class DirectMethod
    {

        /// <summary>
        /// Creates an instance of this class.
        /// </summary>
        /// <param name="method">The method information.</param>
        internal DirectMethod(MethodInfo method)
        {
            this.Method = method;
            this.IsForm = Utility.HasAttribute(method, typeof(DirectMethodFormAttribute));
            this.Name = method.Name;
            this.Parameters = method.GetParameters().Length;
        }

        /// <summary>
        /// Gets the method info.
        /// </summary>
        internal MethodInfo Method
        {
            get;
            private set;
        }

        /// <summary>
        /// Gets whether the method is a form method;
        /// </summary>
        internal bool IsForm
        {
            get;
            private set;
        }

        /// <summary>
        /// Gets the name of the method.
        /// </summary>
        internal string Name
        {
            get;
            private set;
        }

        /// <summary>
        /// Gets the number of parameters for the method.
        /// </summary>
        internal int Parameters
        {
            get;
            private set;
        }

        /// <summary>
        /// Write API JSON.
        /// </summary>
        /// <param name="jw">The JSON writer.</param>
        internal void Write(JsonTextWriter jw)
        {
            jw.WriteStartObject();
            Utility.WriteProperty<string>(jw, "name", this.Name);
            Utility.WriteProperty<int>(jw, "len", this.Parameters);
            Utility.WriteProperty<bool>(jw, "formHandler", this.IsForm);
            jw.WriteEndObject();
        }

        /// <summary>
        /// Checks whether the passed method is a direct method.
        /// </summary>
        /// <param name="mi">The method to check.</param>
        /// <returns>True if the method is a direct method.</returns>
        internal static bool IsMethod(MethodInfo mi)
        {
            return Utility.HasAttribute(mi, typeof(DirectMethodAttribute));
        }

    }
}
