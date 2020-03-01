package tutorial;

import java.util.List;

import org.zkoss.zk.ui.Component;

public interface CarService {

	/**
	 * Retrieve all cars in the catalog.
	 * @return all cars
	 */
	public List<Car> findAll();
	
	/**
	 * search cars according to keyword in model and make.
	 * @param keyword for search
	 * @return list of car that match the keyword
	 */
	public List<Car> search(String keyword);
	public boolean add(Car car);
	public boolean delete(int carId);
	public Car selectCar(int carId);
    public boolean update(Car car);

}
