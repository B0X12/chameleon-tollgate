using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AuthClient.tollgate.otp.service
{
    class ReturnMessage
    {
		public const String SUCCESS = "SUCCESS";
		public const String FAIL = "FAIL";
		public const String UNKNOWN = "UNKNOWN";

		public const String FAIL_INFORMATION = "Fail INFORMATION";

		public const String FAIL_REGISTER_INFORMATION = "Fail Register INFORMATION";
		public const String FAIL_REGISTER_UNKNOWN = "Fail Register UNKNOWN";

		public const String FAIL_VERIFY_INFORMATION = "Fail Verify INFORMATION";
		public const String FAIL_VERIFY_UNKNOWN = "Fail Verify UNKNOWN";
		public const String FAIL_VERIFY_TIMEOUT = "Fail Verify TIMEOUT";
		public const String FAIL_VERIFY = "Fail Verify";
	}
}
