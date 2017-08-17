package helpers;

public abstract class CustomLoadableComponent<T extends CustomLoadableComponent<T>> {

    @SuppressWarnings("unchecked")
    public T get() {
        try {
            isLoaded();
            return (T) this;
        } catch (Error e) {
            // This is the extra line of code 
            System.out.println("Error encountered during page load: " + e.getMessage());
            load();
        }

        isLoaded();

        return (T) this;
    }

    protected abstract void load();

    protected abstract void isLoaded() throws Error;
}
