package tutorial;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class UpdateCarModalDialogController extends SelectorComposer<Component>{
  private static final long serialVersionsUID =1L;
  
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
  @Wire
  private Label msg;
  private CarService carService = new CarServiceImpl("jdbc:mysql://localhost:3306/training??useUnicode=true&&characterEncoding=UTF-8","root","");
  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Integer carId = (Integer)Executions.getCurrent().getArg().get("carId");
    msg.setValue(carId.toString());
    Car car = carService.selectCar(carId);
    model.setValue(car.getModel());
    make.setValue(car.getMake());
    preview.setValue(car.getPreview());
    description.setValue(car.getDescription());
    price.setValue(car.getPrice().toString());
  }

  @Listen("onClick=#updateCarBtn")
  public void editCar() {
	  String src="";
	  if(img.getContent()!=null) {
		  saveImage();
		  src = "img/"+createImgName(img.getContent().getName());
	  }
	  else {
		  src=preview.getValue();
	  }
	  Car car=new Car(Integer.valueOf(msg.getValue()),model.getValue(), make.getValue(), description.getValue(), src, Integer.valueOf( price.getValue()));
	  boolean result = carService.update(car);
		if(result) {
			Messagebox.show("Ban da sua thanh cong");
			
		}else {
			Messagebox.show("Thao tac sua bi loi");
		}
		Executions.sendRedirect("searchMvc.zul");
  }
  
  private String createImgName(String imgName) {
	  String[] imgExt=imgName.split("\\.");
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

	


