package tutorial;


import java.util.HashMap;
import java.util.List;
import java.util.Set;



import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.*;
import org.zkoss.zul.*;
import org.zkoss.zul.ext.Selectable;

public class HomeController extends SelectorComposer<Component> {

  private static final long serialVersionUID = 1L;
  
 
  @Wire
  private Listbox carListbox;
  @Wire
  private Label modelLabel;
  @Wire
  private Label makeLabel;
  @Wire
  private Label priceLabel;
  @Wire
  private Label descriptionLabel;
  @Wire
  private Image previewImage;
  
  @Wire
  private Hlayout carDetail;
  
  @Wire
  private A back;
  
  private CarService carService = new CarServiceImpl("jdbc:mysql://localhost:3306/training??useUnicode=true&&characterEncoding=UTF-8", "root", "");
  
  
  @Listen("onClick=#back")
  public void backListCar() {
	  carListbox.setVisible(false);
	  carDetail.setVisible(true);
	  
  }
  @Override
  public void doAfterCompose(Component comp) throws Exception {
    // TODO Auto-generated method stub
    super.doAfterCompose(comp);
    String keyword = (String) Executions.getCurrent().getParameter("type");
    
    
    List<Car> result = carService.search(keyword);
    carListbox.setModel(new ListModelList<Car>(result));
   
  }
  
  @Listen("onSelect = #carListbox")
  public void showDetail(){
    Set<Car> selection = ((Selectable<Car>)carListbox.getModel()).getSelection();
    if (selection!=null && !selection.isEmpty()){
      Car selected = selection.iterator().next();
      previewImage.setSrc(selected.getPreview());
      modelLabel.setValue(selected.getModel());
      makeLabel.setValue(selected.getMake());
      priceLabel.setValue(selected.getPrice().toString());
      descriptionLabel.setValue(selected.getDescription());
    }
    carListbox.setVisible(false);
    carDetail.setVisible(true);
  }
  
}
  