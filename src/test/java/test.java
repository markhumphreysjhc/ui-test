import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.*;
import com.sun.jna.platform.win32.COM.COMUtils;
import com.sun.jna.platform.win32.COM.Unknown;
import com.sun.jna.ptr.PointerByReference;

/**
 * Created by HumphreysM on 08/07/2016.
 */
public class test {

    public void run() {
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

            // The root element
            PointerByReference pRoot = new PointerByReference();

            ia.GetRootElement(pRoot);

            processRootElement(pRoot);

            getDesktopObject(ia);
        }
    }

    private IUIAutomationElement root;

    public static final Guid.GUID CLSID_CUIAutomation = new Guid.GUID("{FF48DBA4-60EF-4201-AA87-54103EEF594E}");

    private void processRootElement(PointerByReference reference) {
        Unknown uRoot = new Unknown(reference.getValue());

        Guid.REFIID refiidElement = new Guid.REFIID(IUIAutomationElement.IID_IUIAUTOMATION_ELEMENT);

        WinNT.HRESULT result0 = uRoot.QueryInterface(refiidElement, reference);

        if(COMUtils.SUCCEEDED(result0)) {
            this.root = IUIAutomationElement.Converter.PointerToIUIAutomationElement(reference);

            PointerByReference sr = new PointerByReference();

            this.root.get_CurrentName(sr);

            String wideSR = sr.getValue().getWideString(0);

            PointerByReference sr1 = new PointerByReference();

            this.root.get_CurrentClassName(sr1);

            String wideSR1 = sr1.getValue().getWideString(0);

            String test = uRoot.toString();
            String name = test;
        }
    }

    private void getDesktopObject(IUIAutomation ia) {
        // Get some conditions
        PointerByReference pCondition1 = new PointerByReference();
        PointerByReference pCondition2 = new PointerByReference();

        Variant.VARIANT var1 = new Variant.VARIANT.ByReference();
        Variant.VARIANT var2 = new Variant.VARIANT.ByReference();

        var1.setValue(Variant.VT_INT, ControlType.Window);
        var2.setValue(Variant.VT_BSTR, new WTypes.BSTR("Form1"));

        // I think this is the problem!!!! Variants bah

        ia.CreatePropertyCondition(PropertyID.ControlType.getValue(), var1, pCondition1);
        ia.CreatePropertyCondition(PropertyID.Name.getValue(), var2, pCondition2);

        Unknown unkCondition1 = new Unknown(pCondition1.getValue());
        PointerByReference pUnknown1 = new PointerByReference();

        Guid.REFIID refiid1 = new Guid.REFIID(IUIAutomationCondition.IID_IUIAUTOMATION_CONDITION);

        WinNT.HRESULT result1 = unkCondition1.QueryInterface(refiid1, pUnknown1);
        if (COMUtils.SUCCEEDED(result1)) {
            String test = pUnknown1.toString();
        }

        Unknown unkCondition2 = new Unknown(pCondition2.getValue());
        PointerByReference pUnknown2 = new PointerByReference();

        Guid.REFIID refiid2 = new Guid.REFIID(IUIAutomationCondition.IID_IUIAUTOMATION_CONDITION);

        WinNT.HRESULT result2 = unkCondition1.QueryInterface(refiid2, pUnknown2);
        if (COMUtils.SUCCEEDED(result2)) {
            String test = pUnknown2.toString();
        }

        PointerByReference pTrueCondition1 = new PointerByReference();

        ia.CreateTrueCondition(pTrueCondition1);

        Unknown unkCondition3 = new Unknown(pTrueCondition1.getValue());
        PointerByReference pUnknown3 = new PointerByReference();

        Guid.REFIID refiid3 = new Guid.REFIID(IUIAutomationCondition.IID_IUIAUTOMATION_CONDITION);

        WinNT.HRESULT result3 = unkCondition1.QueryInterface(refiid3, pUnknown1);
        if (COMUtils.SUCCEEDED(result3)) {
            String test = pUnknown3.toString();
        }

        PointerByReference pAll = new PointerByReference();

        this.root.FindAll(TreeScope.Children, pTrueCondition1.getPointer(), pAll);

        String here = "got here";
    }
}
