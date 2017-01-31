package FMRadio;

/**
 * Created by Wang on 2017/1/27.
 */
public interface Subject {
    public void registerObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObservers();
}
