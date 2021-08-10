using System;

namespace AuthClient.tollgate.rest
{
    class InvalidArgumentException : Exception
    {
        public InvalidArgumentException()
        {
        }

        public InvalidArgumentException(string message) : base(message)
        {
        }

        public Method method
        {
            get; set;
        }
    }
}
