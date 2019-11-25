package group.ecofuels.window;

import java.util.List;

import org.compiere.process.ProcessInfo;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

public interface IJRViewerProviderList {
	public void openViewer(List<JasperPrint> jasperPrintList, ProcessInfo pi) throws JRException;
}
