package group.ecofuels.window;

import org.compiere.process.ProcessInfo;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

public interface IJRViewerProvider {
	public void openViewer(JasperPrint jasperPrint, ProcessInfo pi) throws JRException;
}
