# iDempiereJasperPlus
This iDempiere plugin adds an archive button to the JasperReport Document preview

## Installation
The Plugin is currently only available as source code. It will be released as jar for convenient installation via the console once its stability is established.
If you should really really want the thing now and are prepared to supply me with bug reports once you run into any trouble using it I will be happy to send you a prerelease jar.
## Here is how it works:

In ReportStarter.startProcess0() I am adding a couple of missing parameters to the ProcessInfo:

    private boolean startProcess0(Properties ctx, ProcessInfo pi, Trx trx)
    {
        int AD_Table_ID = Integer.parseInt(ctx.getProperty("1|0|_TabInfo_AD_Table_ID")); // This one works but still looks a bit fishy to me.
        int Record_ID = pi.getRecord_ID();
        String table_Name = MTable.getTableName(ctx, AD_Table_ID);
        String sql = "Select documentno FROM " + table_Name + " WHERE " + table_Name + "_ID = " + Record_ID;
        String docNo = DB.getSQLValueString(trx == null ? null : trx.getTrxName(), sql);

        pi.setTable_ID(AD_Table_ID); // AD_Table_ID is 0 initially - maybe some bug somewhere up the chain?
        pi.setTitle(docNo); // setting the title (and pdf file name) to the document number
        processInfo = pi;
       (...)

instead of the mere title the viewer provider hands the entire ProcessInfo to the viewer window:
    public class GhgJRViewerProvider implements IJRViewerProvider, IJRViewerProviderList {
        public void openViewer(final JasperPrint jasperPrint, final ProcessInfo pi) {
       (...)

The JR viewer keeps the ProcessInfo reference as a member variable
    public GhgJRViewer(JasperPrint jasperPrint, ProcessInfo pi) {
        super();
        m_pi = pi;
        m_title = pi.getTitle();

It is now available to cmd_archive() where the archive can be properly instantiated.
    private void cmd_archive ()
    {
        boolean success = false;
        byte[] data = media.getByteData();
        if (data != null) {
            Properties ctx = Env.getCtx();

            PrintInfo info = new PrintInfo(m_pi);

            MArchive archive = new MArchive (ctx, info, null);
            archive.setBinaryData(data);
            success = archive.save();
        }
        if (success)
            FDialog.info(m_WindowNo, this, "Archived");
        else
            FDialog.error(m_WindowNo, this, "ArchiveError");
    } // cmd_archive

## What is missing/Issues: 
- Some code review is needed. 
- Some thorough testing.
- The jasperprintlist implementation of the viewer window is missing.
- The archive viewer lists the archived documents as reports and not as documents. How could that be put right?
