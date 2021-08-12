namespace AuthClient.tollgate.home.history
{
    class HistoryPack
    {
        public bool result;
        public long timestamp;

        public HistoryPack(bool result, long timestamp)
        {
            this.result = result;
            this.timestamp = timestamp;
        }
    }
}
