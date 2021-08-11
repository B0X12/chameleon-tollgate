using AuthClient.tollgate.rest;
using Newtonsoft.Json;
using System.Windows.Forms;

namespace AuthClient.tollgate.home.setting
{
    public partial class SettingAccount : UserControl
    {
        public string ID
        {
            get { return edit_id.Text; }
            set { edit_id.Text = value; }
        }

        public SettingAccount()
        {
            InitializeComponent();
        }

        private void edit_confirm_KeyPress(object sender, KeyPressEventArgs e)
        {
            edit_confirm.IsCheck = edit_pwd.Text == edit_confirm.Text + e.KeyChar;
        }

        private void edit_pwd_KeyPress(object sender, KeyPressEventArgs e)
        {
            edit_confirm.IsCheck = edit_confirm.Text == edit_pwd.Text + e.KeyChar;
        }

        private void img_edit_Click(object sender, System.EventArgs e)
        {
            if(!edit_confirm.IsCheck)
            {
                MessageBox.Show("비밀번호가 다릅니다.");
                return;
            }

            QueryString qs = new QueryString("pwd", Util.EncryptSHA512(edit_pwd.Text));
            qs.AddQueryString("timestamp", Util.GetCurrentTimestamp());
            RestResult result = new HttpCommunication(Method.PUT, URLPath.SET_PWD + Config.GetCurrentUser(), qs, null).SendRequest();
            if(result.statusCode != System.Net.HttpStatusCode.OK)
            {
                MessageBox.Show("알 수 없는 오류가 발생했습니다.");
                return;
            }
            ResponseData<bool> data = JsonConvert.DeserializeObject<ResponseData<bool>>(result.jsonResult);
            if (data.getResult())
                MessageBox.Show("비밀번호 변경에 성공했습니다.");
            else
                MessageBox.Show("비밀번호 변경에 실패했습니다.");
        }
    }
}
