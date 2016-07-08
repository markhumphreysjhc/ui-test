import com.sun.jna.Function;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.*;
import com.sun.jna.platform.win32.COM.COMUtils;
import com.sun.jna.platform.win32.COM.IUnknown;
import com.sun.jna.platform.win32.COM.Unknown;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;
import java.util.Arrays;

/**
 * Created by HumphreysM on 27/06/2016.
 */
public class Main {
    public static final Guid.GUID CLSID_CUIAutomation = new Guid.GUID("{FF48DBA4-60EF-4201-AA87-54103EEF594E}");

    private static void processRootElement(PointerByReference reference) {
        Unknown uRoot = new Unknown(reference.getValue());

        Guid.REFIID refiidElement = new Guid.REFIID(IUIAutomationElement.IID_IUIAUTOMATION_ELEMENT);

        WinNT.HRESULT result0 = uRoot.QueryInterface(refiidElement, reference);

        if(COMUtils.SUCCEEDED(result0)) {
            IUIAutomationElement root = IUIAutomationElement.Converter.PointerToIUIAutomationElement(reference);

            PointerByReference sr = new PointerByReference();

            root.get_CurrentName(sr);

            String wideSR = sr.getValue().getWideString(0);

            PointerByReference sr1 = new PointerByReference();

            root.get_CurrentClassName(sr1);

            String wideSR1 = sr1.getValue().getWideString(0);

            String test = uRoot.toString();
            String name = test;
        }
    }

    public static void main(String[] args) {
        Ole32.INSTANCE.CoInitializeEx(Pointer.NULL, Ole32.COINIT_APARTMENTTHREADED);

        PointerByReference pbr = new PointerByReference();

        WinNT.HRESULT hr = Ole32.INSTANCE.CoCreateInstance(
                CLSID_CUIAutomation,
                null,
                WTypes.CLSCTX_SERVER,
                IUIAutomation.IID_IUIAUTOMATION,
                pbr);

        COMUtils.checkRC(hr);

        Unknown unk = new Unknown(pbr.getValue());

        PointerByReference pbr1 = new PointerByReference();

        Guid.REFIID refiid = new Guid.REFIID(IUIAutomation.IID_IUIAUTOMATION);

        WinNT.HRESULT result = unk.QueryInterface(refiid, pbr1);
        if (COMUtils.SUCCEEDED(result)) {
            IUIAutomation ia = IUIAutomation.Converter.PointerToIUIAutomation(pbr1);

            // Get the root element

            PointerByReference pRoot = new PointerByReference();

            ia.GetRootElement(pRoot);

            processRootElement(pRoot);

            getDesktopObject(ia);
        }
    }

    private static void getDesktopObject(IUIAutomation ia) {
        // Get some conditions
        PointerByReference pCondition1 = new PointerByReference();
        PointerByReference pCondition2 = new PointerByReference();

        Variant.VARIANT var1 = new Variant.VARIANT.ByReference();
        Variant.VARIANT var2 = new Variant.VARIANT.ByReference();

        var1.setValue(Variant.VT_INT, ControlType.Window);
        var2.setValue(Variant.VT_BSTR, new WTypes.BSTR("Form1"));

        // I think this is the problem!!!! Variants bah

        ia.CreatePropertyCondition(PropertyID.Name.getValue(), var1, pCondition1);
        ia.CreatePropertyCondition(PropertyID.ControlType.getValue(), var2, pCondition2);

        Unknown unkCondition1 = new Unknown(pCondition1.getValue());
        PointerByReference pUnknown1 = new PointerByReference();

        Guid.REFIID refiid2 = new Guid.REFIID(IUIAutomationCondition.IID_IUIAUTOMATION_CONDITION);

        WinNT.HRESULT result2 = unkCondition1.QueryInterface(refiid2, pUnknown1);
        if (COMUtils.SUCCEEDED(result2)) {
            String test = pUnknown1.toString();
            String name = test;
        }

        PointerByReference pCondition = new PointerByReference();
        ia.CreateAndCondition(pCondition1.getPointer(), pCondition2.getPointer(), pCondition);
    }
}
