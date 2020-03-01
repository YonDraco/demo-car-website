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

public class SearchController extends SelectorComposer<Component> {

  private static final long serialVersionUID = 1L;
  
  @Wire
  private Textbox keywordBox;
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
  
  
  
  private CarService carService = new CarServiceImpl("jdbc:mysql://localhost:3306/training??useUnicode=true&&characterEncoding=UTF-8", "root", "");
  
  @Override
  public void doAfterCompose(Component comp) throws Exception {
    // TODO Auto-generated method stub
    super.doAfterCompose(comp);
    
    List<Car> result = carService.search(null);
    carListbox.setModel(new ListModelList<Car>(result));
  }
  
  @Listen("onClick = #searchButton")
  public void search(){
    String keyword = keywordBox.getValue();
    List<Car> result = carService.search(keyword);
    carListbox.setModel(new ListModelList<Car>(result));
  }
  
  @Listen("onDelete=#carListbox")
  public void doDelete(ForwardEvent evt) {
    Messagebox.show("Are u sure to delete?","Delete?", Messagebox.YES | Messagebox.NO, 
        Messagebox.QUESTION, new EventListener<Event>() {
          @Override
          public void onEvent(final Event confirmEvt) throws InterruptedException {
            if (Messagebox.ON_YES.equals(confirmEvt.getName())) {
              Button deleteBtn = (Button)evt.getOrigin().getTarget();
              String deleteBtnId = deleteBtn.getId();
              int carId = Integer.valueOf(deleteBtnId.substring(6));
              
              //Messagebox.show("Car Id : " +carId);
              boolean result = carService.delete(carId);
              if (result){
                Messagebox.show("Ban da xoa thanh cong");
                Executions.sendRedirect("searchMvc.zul");
              }
             else {
               Messagebox.show("Huy xoa");
               
               return;
             }  
          }
        }
      }
    );
  }
  
  @Listen("onClick = #addButton")
  public void showAddModal() {
    Window window= (Window)Executions.createComponents("addCar.zul",null,null);
    window.doModal();
  }
  
  @Listen("onEdit=#carListbox")
  public void showUpdateModalDialog(ForwardEvent evt) {
    Button editBtn=(Button)evt.getOrigin().getTarget();
    String editBtnId = editBtn.getId();
    Integer carId = Integer.valueOf(editBtnId.substring(4));
    final HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("carId", carId);
    Window window= (Window)Executions.createComponents("editCar.zul",null,map);
    window.doModal();
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
  }
}