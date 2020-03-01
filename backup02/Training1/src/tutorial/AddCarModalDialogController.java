package tutorial;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Image;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;



public class AddCarModalDialogController extends SelectorComposer<Component> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Wire
	private Window modalDialog;
	@Wire
	private Textbox model;
	@Wire
	private Textbox make;
	@Wire
	private Textbox preview;
	@Wire
	private Textbox description;
	@Wire
	private Textbox price;
	@Wire
	Image img;
	
	
	private CarService carService = new CarServiceImpl("jdbc:mysql://localhost:3306/training??useUnicode=true&&characterEncoding=UTF-8","root","");

	private String imgName;
	@Listen("onClick=#addCarBtn")
	public void addCar() {
		saveImage();
		String preview = "img/"+ createImgName(img.getContent().getName());
		Car car=new Car(model.getValue(), make.getValue(), description.getValue(), preview, Integer.valueOf( price.getValue()));
		boolean result = carService.add(car);
		if(result) {
			Messagebox.show("Ban da them thanh cong");
			
		}else {
			Messagebox.show("Thao tac them bi loi");
		}
		modalDialog.detach();
		Executions.sendRedirect("searchMvc.zul");
	}
	private String createImgName(String imgName) {
		// TODO Auto-generated method stub
		String[] imgExt=imgName.split("\\.");
		System.out.print(imgExt[0]);
		String nameStd = model.getValue()+"."+imgExt[1];
		return nameStd;
	}
	private void saveImage() {
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		String imgDir =
	Executions.getCurrent().getDesktop().getWebApp().getRealPath("/img/");
		try {
			InputStream fin = img.getContent().getStreamData();
			in = new BufferedInputStream(fin);
			File file = new File(imgDir + img.getContent().getName());
				OutputStream fout = new FileOutputStream(file);
				out = new BufferedOutputStream(fout);
				byte buffer[] = new byte[1024];
				int ch = in.read(buffer);
				while (ch !=-1) {
					out.write(buffer, 0, ch);
					ch = in.read(buffer);
				}
		} catch (IOException e) {
			throw new RuntimeException(e);	
		} catch (Exception e) {
			throw new RuntimeException(e);	
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
				
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
