namespace AuthClient.tollgate.account.dto
{
    class Account
    {
        public string id { get; set; }
        public string pwd { get; set; }

        public Account(string id, string pwd)
        {
            this.id = id;
            this.pwd = pwd;
        }
    }
}
