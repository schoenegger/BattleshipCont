package GameUtilities.Field;

/**
 * 
 * @author Schoenegger / Purkart / Koch
 *
 */
public class FieldElement
{
    private FieldState fieldState;
    private boolean isTaken;

    /**
     * get the field state
     * 
     * @return FieldState
     */
    public FieldState getFieldState()
    {
	return fieldState;
    }

    /**
     * is on this field position a ship
     * 
     * @return boolean
     */
    public boolean isTaken()
    {
	return this.isTaken;
    }

    /**
     * set the field state
     * 
     * @param fieldState
     */
    public void setFieldState(FieldState fieldState)
    {
	this.fieldState = fieldState;
    }

    /**
     * set a ship on this position
     */
    public void setTaken()
    {
	this.isTaken = true;
    }
}
