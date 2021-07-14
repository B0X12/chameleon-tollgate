using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RestClient.rest
{
    class ResponseData <T>
    {
        private int httpStatus;
        private T result;
        private long timestamp;

        public ResponseData(int httpStatus, T result, long timestamp)
        {
            this.httpStatus = httpStatus;
            this.result = result;
            this.timestamp = timestamp;
        }

        public int getHttpStatus()
        {
            return this.httpStatus;
        }

        public T getResult()
        {
            return this.result;
        }

        public long getTimestamp()
        {
            return this.timestamp;
        }
    }

    class ErrorData
    {
        private int httpStatus;
        private string message;

        public ErrorData(int httpStatus, string message)
        {
            this.httpStatus = httpStatus;
            this.message = message;
        }

        public int getHttpStatus()
        {
            return this.httpStatus;
        }

        public string getMessage()
        {
            return this.message;
        }
    }
}
