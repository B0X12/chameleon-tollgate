using AuthClient.tollgate.rest;
using AuthClient.tollgate.usb.dto;
using Newtonsoft.Json;
using System.Collections.Generic;
using System.Net;
using System.Windows.Forms;

namespace AuthClient.tollgate.usb.service
{
    class USBService
    {
        public List<USBInfo> GetRegisteredUSBList(string id)
        {
            // 통신 준비
            long currentTimestamp = Util.GetCurrentTimestamp();
            QueryString qs = new QueryString("timestamp", currentTimestamp);
            HttpCommunication hc = new HttpCommunication(Method.GET, URLPath.AUTH_USB + id, qs);

            RestResult result = hc.SendRequest();

            // 서버 응답 코드가 200일 경우
            if (result.statusCode == HttpStatusCode.OK)
            {
                ResponseData<List<USBInfo>> rd = JsonConvert.DeserializeObject<ResponseData<List<USBInfo>>>(result.jsonResult);
                List<USBInfo> usbList = rd.getResult();

                // 타임 스탬프 일치 / 해당 컴퓨터와 연동된 컴퓨터(UID) 존재함
                if (rd.getTimestamp().Equals(currentTimestamp))
                {
                    return usbList;
                }
            }

            return null;
        }

        public bool RegisterUSBInfo(USBInfo usbInfo)
        {
            // 통신 준비
            long currentTimestamp = Util.GetCurrentTimestamp();
            QueryString qs = new QueryString("timestamp", currentTimestamp);
            HttpCommunication hc = new HttpCommunication(Method.POST, URLPath.REGISTER_USB, qs, usbInfo);

            RestResult result = hc.SendRequest();

            // 서버 응답 코드가 200일 경우
            if (result.statusCode == HttpStatusCode.OK)
            {
                ResponseData<bool> rd = JsonConvert.DeserializeObject<ResponseData<bool>>(result.jsonResult);

                // 타임 스탬프 일치 / 해당 컴퓨터와 연동된 컴퓨터(UID) 존재함
                if (rd.getTimestamp().Equals(currentTimestamp))
                {
                    if (rd.getResult() == true)
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
            }
            else
            {
                MessageBox.Show("USB를 등록하는 도중 오류가 발생하였습니다");
            }
            return false;
        }

        public bool UnregisterUSBInfo(USBInfo usbInfo)
        {
            // 통신 준비
            long currentTimestamp = Util.GetCurrentTimestamp();
            QueryString qs = new QueryString("timestamp", currentTimestamp);
            HttpCommunication hc = new HttpCommunication(Method.DELETE, URLPath.REGISTER_USB + usbInfo.id + "/" + usbInfo.usb_id, qs);

            RestResult result = hc.SendRequest();

            // 서버 응답 코드가 200일 경우
            if (result.statusCode == HttpStatusCode.OK)
            {
                ResponseData<bool> rd = JsonConvert.DeserializeObject<ResponseData<bool>>(result.jsonResult);

                // 타임 스탬프 일치 / 해당 컴퓨터와 연동된 컴퓨터(UID) 존재함
                if (rd.getTimestamp().Equals(currentTimestamp))
                {
                    if (rd.getResult() == true)
                    {
                        return true;
                    }
                }
            }

            return false;
        }

        public bool UpdateUSBAlias(USBInfo usbInfo)
        {
            // 통신 준비
            long currentTimestamp = Util.GetCurrentTimestamp();
            QueryString qs = new QueryString("timestamp", currentTimestamp);
            HttpCommunication hc = new HttpCommunication(Method.PUT, URLPath.UPDATE_USB_ALIAS, qs, usbInfo);

            RestResult result = hc.SendRequest();

            // 서버 응답 코드가 200일 경우
            if (result.statusCode == HttpStatusCode.OK)
            {
                ResponseData<bool> rd = JsonConvert.DeserializeObject<ResponseData<bool>>(result.jsonResult);

                // 타임 스탬프 일치 / 해당 컴퓨터와 연동된 컴퓨터(UID) 존재함
                if (rd.getTimestamp().Equals(currentTimestamp))
                {
                    if (rd.getResult() == true)
                    {
                        return true;
                    }
                }
            }

            return false;
        }
    }
}
