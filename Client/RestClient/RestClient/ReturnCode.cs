using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RestClient
{
    public enum ReturnCode
    {
        // 프로그램 리턴 코드 - 성공
        RESULT_CONNECTION_SUCCESS = 100,

        // 프로그램 리턴 코드 - 실패
        RESULT_CONNECTION_FAILED,
        RESULT_CONFIG_FILE_COMPROMISED,
        RESULT_UNAUTHORIZED_ACCESS,
        RESULT_TIMESTAMP_MISMATCH,
        RESULT_CONNECTION_TIMEOUT,
        RESULT_UNKNOWN_ERROR,
    }
}
