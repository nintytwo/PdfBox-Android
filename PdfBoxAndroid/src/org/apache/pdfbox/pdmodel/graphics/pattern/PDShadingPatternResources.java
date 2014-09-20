package org.apache.pdfbox.pdmodel.graphics.pattern;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSFloat;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSNumber;
import org.apache.pdfbox.pdmodel.graphics.PDExtendedGraphicsState;
import org.apache.pdfbox.pdmodel.graphics.shading.PDShadingResources;
import org.apache.pdfbox.util.Matrix;

/**
 * This represents the resources for a shading pattern.
 *
 */
public class PDShadingPatternResources extends PDPatternResources
{
    private PDExtendedGraphicsState extendedGraphicsState;
    private PDShadingResources shading;
    private COSArray matrix = null;

    /**
     * Log instance.
     */
    private static final Log LOG = LogFactory.getLog(PDShadingPatternResources.class);

    /**
     * Default constructor.
     */
    public PDShadingPatternResources()
    {
        super();
        getCOSDictionary().setInt(COSName.PATTERN_TYPE, PDPatternResources.SHADING_PATTERN);
    }

    /**
     * Prepopulated pattern resources.
     *
     * @param resourceDictionary The COSDictionary for this pattern resource.
     */
    public PDShadingPatternResources( COSDictionary resourceDictionary )
    {
        super(resourceDictionary);
    }

    /**
     * {@inheritDoc}
     */
    public int getPatternType()
    {
        return PDPatternResources.SHADING_PATTERN;
    }

    /**
     * This will get the optional Matrix of a Pattern.
     * It maps the form space into the user space
     * @return the form matrix
     */
    public Matrix getMatrix()
    {
        Matrix returnMatrix = null;
        if (matrix == null)
        {
            matrix = (COSArray)getCOSDictionary().getDictionaryObject( COSName.MATRIX );
        }
        if( matrix != null )
        {
            returnMatrix = new Matrix();
            returnMatrix.setValue(0, 0, ((COSNumber) matrix.get(0)).floatValue());
            returnMatrix.setValue(0, 1, ((COSNumber) matrix.get(1)).floatValue());
            returnMatrix.setValue(1, 0, ((COSNumber) matrix.get(2)).floatValue());
            returnMatrix.setValue(1, 1, ((COSNumber) matrix.get(3)).floatValue());
            returnMatrix.setValue(2, 0, ((COSNumber) matrix.get(4)).floatValue());
            returnMatrix.setValue(2, 1, ((COSNumber) matrix.get(5)).floatValue());
        }
        return returnMatrix;
    }

    /**
     * Sets the optional Matrix entry for the Pattern.
     * @param transform the transformation matrix
     */
    public void setMatrix(android.graphics.Matrix transform)
    {
        matrix = new COSArray();
        float[] values = new float[9];
        transform.getValues(values);
        for (int i = 0; i < 6; i++)
        {
            matrix.add(new COSFloat((float)values[i]));
        }
        getCOSDictionary().setItem(COSName.MATRIX, matrix);
    }

    /**
     * This will get the extended graphics state for this pattern.
     *
     * @return The extended graphics state for this pattern.
     */
    public PDExtendedGraphicsState getExtendedGraphicsState()
    {
        if (extendedGraphicsState == null) 
        {
            COSDictionary dictionary = (COSDictionary)getCOSDictionary().getDictionaryObject( COSName.EXT_G_STATE );
            if( dictionary != null )
            {
                extendedGraphicsState = new PDExtendedGraphicsState( dictionary );
            }
        }
        return extendedGraphicsState;
    }

    /**
     * This will set the extended graphics state for this pattern.
     *
     * @param extendedGraphicsState The new extended graphics state for this pattern.
     */
    public void setExtendedGraphicsState( PDExtendedGraphicsState extendedGraphicsState )
    {
        this.extendedGraphicsState = extendedGraphicsState;
        if (extendedGraphicsState != null)
        {
            getCOSDictionary().setItem( COSName.EXT_G_STATE, extendedGraphicsState );
        }
        else
        {
            getCOSDictionary().removeItem(COSName.EXT_G_STATE);
        }
    }

    /**
     * This will get the shading resources for this pattern.
     *
     * @return The shading resourcesfor this pattern.
     * 
     * @throws IOException if something went wrong
     */
    public PDShadingResources getShading() throws IOException
    {
        if (shading == null) 
        {
            COSDictionary dictionary = (COSDictionary)getCOSDictionary().getDictionaryObject( COSName.SHADING );
            if( dictionary != null )
            {
                shading = PDShadingResources.create(dictionary);
            }
        }
        return shading;
    }

    /**
     * This will set the shading resources for this pattern.
     *
     * @param shadingResources The new shading resources for this pattern.
     */
    public void setShading( PDShadingResources shadingResources )
    {
        shading = shadingResources;
        if (shadingResources != null)
        {
            getCOSDictionary().setItem( COSName.SHADING, shadingResources );
        }
        else
        {
            getCOSDictionary().removeItem(COSName.SHADING);
        }
    }

    /**
     * {@inheritDoc}
     */
//    public Paint getPaint(int pageHeight) throws IOException
//    {
//        Paint paint = null;
//        PDShadingResources shadingResources = getShading();
//        int shadingType = shadingResources != null ? shadingResources.getShadingType() : 0;
//        switch (shadingType)
//        {
//            case PDShadingResources.SHADING_TYPE1: 
//                paint = new Type1ShadingPaint((PDShadingType1)getShading(), getMatrix(), pageHeight);
//                break;
//            case PDShadingResources.SHADING_TYPE2:
//                paint = new AxialShadingPaint((PDShadingType2)getShading(), getMatrix(), pageHeight);
//                break;
//            case PDShadingResources.SHADING_TYPE3:
//                paint = new RadialShadingPaint((PDShadingType3)getShading(), getMatrix(), pageHeight);
//                break;
//            case PDShadingResources.SHADING_TYPE4:
//                paint = new Type4ShadingPaint((PDShadingType4)getShading(), getMatrix(), pageHeight);
//                break;
//            case PDShadingResources.SHADING_TYPE5:
//                paint = new Type5ShadingPaint((PDShadingType5)getShading(), getMatrix(), pageHeight);
//                break;
//            case PDShadingResources.SHADING_TYPE6:
//            case PDShadingResources.SHADING_TYPE7:
//                LOG.debug( "Error: Unsupported shading type " + shadingType );
//                break;
//            default:
//                throw new IOException( "Error: Unknown shading type " + shadingType );
//        }
//        return paint;
//    }TODO
}