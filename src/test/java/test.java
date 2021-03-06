import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.*;
import com.sun.jna.platform.win32.COM.COMUtils;
import com.sun.jna.platform.win32.COM.Unknown;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import org.apache.log4j.Logger;

/**
 * Created by HumphreysM on 08/07/2016.
 */
public class test {

    protected Logger logger = Logger.getLogger(test.class.getName());

    public void run() {
        Ole32.INSTANCE.CoInitializeEx(Pointer.NULL, Ole32.COINIT_APARTMENTTHREADED);

        PointerByReference pbr = new PointerByReference();

        WinNT.HRESULT hr = Ole32.INSTANCE.CoCreateInstance(
                CLSID_CUIAutomation,
                null,
                WTypes.CLSCTX_SERVER,
                IUIAutomation.IID,
                pbr);

        COMUtils.checkRC(hr);

        Unknown unk = new Unknown(pbr.getValue());

        PointerByReference pbr1 = new PointerByReference();

        Guid.REFIID refiid = new Guid.REFIID(IUIAutomation.IID);

        WinNT.HRESULT result = unk.QueryInterface(refiid, pbr1);
        if (COMUtils.SUCCEEDED(result)) {
            IUIAutomation ia = IUIAutomation.Converter.PointerToInterface(pbr1);

            // The root element
            PointerByReference pRoot = new PointerByReference();

            ia.GetRootElement(pRoot);

            processRootElement(pRoot);

            IntByReference same = new IntByReference();

            ia.CompareElements(pRoot.getValue(), pRoot.getValue(), same);

            int value = same.getValue();

            getDesktopObject(ia);
        }
    }

    private IUIAutomationElement root;

    public static final Guid.GUID CLSID_CUIAutomation = new Guid.GUID("{FF48DBA4-60EF-4201-AA87-54103EEF594E}");

    private void processRootElement(PointerByReference reference) {
        Unknown uRoot = new Unknown(reference.getValue());

        Guid.REFIID refiidElement = new Guid.REFIID(IUIAutomationElement.IID);

        WinNT.HRESULT result0 = uRoot.QueryInterface(refiidElement, reference);

        if(COMUtils.SUCCEEDED(result0)) {
            this.root = IUIAutomationElement.Converter.PointerToInterface(reference);

            PointerByReference sr = new PointerByReference();

            this.root.get_CurrentName(sr);

            String wideSR = sr.getValue().getWideString(0);

            logger.info(wideSR);

            PointerByReference sr1 = new PointerByReference();

            this.root.get_CurrentClassName(sr1);

            String wideSR1 = sr1.getValue().getWideString(0);

            String test = uRoot.toString();

            logger.info(wideSR1);
        }
    }

    private void getDesktopObject(IUIAutomation ia) {
        PointerByReference pTrueCondition1 = new PointerByReference();

        ia.CreateTrueCondition(pTrueCondition1);

        Unknown unkCondition3 = new Unknown(pTrueCondition1.getValue());
        PointerByReference pUnknown3 = new PointerByReference();

        Guid.REFIID refiid3 = new Guid.REFIID(IUIAutomationCondition.IID);

        Unknown unkCondition1 = new Unknown(pTrueCondition1.getValue());
        PointerByReference pUnknown1 = new PointerByReference();

        WinNT.HRESULT result3 = unkCondition1.QueryInterface(refiid3, pUnknown1);
        if (COMUtils.SUCCEEDED(result3)) {
            String test = pUnknown1.toString();
        }

        PointerByReference pAll = new PointerByReference();

        TreeScope scope = new TreeScope(TreeScope.TreeScope_Children);

        this.root.findAll(scope, pTrueCondition1.getValue(), pAll);

        // Get some conditions
        PointerByReference pCondition1 = new PointerByReference();
        PointerByReference pCondition2 = new PointerByReference();

        Variant.VARIANT.ByValue var1 = new Variant.VARIANT.ByValue();
        Variant.VARIANT.ByValue var2 = new Variant.VARIANT.ByValue();

        var1.setValue(Variant.VT_INT, ControlType.Window);
      //  var2.setValue(Variant.VT_BSTR, new WTypes.BSTR("Form1"));
      //  var1.setValue(Variant.VT_INT, ControlType.Button);

        WTypes.BSTR sysAllocated = OleAuto.INSTANCE.SysAllocString("Form1");
        var2.setValue(Variant.VT_BSTR, sysAllocated);

        // I think this is the problem!!!! Variants bah - changing to not use the strings

        int result11 = ia.CreatePropertyCondition(PropertyID.ControlType.getValue(), var1, pCondition1);
        int result21 = ia.CreatePropertyCondition(PropertyID.Name.getValue(), var2, pCondition2);

        Guid.REFIID refiid1 = new Guid.REFIID(IUIAutomationCondition.IID);

        WinNT.HRESULT result1 = unkCondition1.QueryInterface(refiid1, pUnknown1);
        if (COMUtils.SUCCEEDED(result1)) {
            String test = pUnknown1.toString();
        }

        Unknown unkCondition2 = new Unknown(pCondition2.getValue());
        PointerByReference pUnknown2 = new PointerByReference();

        Guid.REFIID refiid2 = new Guid.REFIID(IUIAutomationCondition.IID);

        WinNT.HRESULT result2 = unkCondition1.QueryInterface(refiid2, pUnknown2);
        if (COMUtils.SUCCEEDED(result2)) {
            String test = pUnknown2.toString();
        }

        PointerByReference pCondition = new PointerByReference();

        int resultAA = ia.CreateAndCondition(pCondition1.getValue(), pCondition2.getValue(), pCondition);

        String here = "got here";

        TreeScope scop1e = new TreeScope(TreeScope.TreeScope_Children);

        int resultAll = this.root.findAll(scop1e, pTrueCondition1.getValue(), pAll);

        // What has come out of findAll ??

        Unknown unkConditionA = new Unknown(pAll.getValue());
        PointerByReference pUnknownA = new PointerByReference();

        Guid.REFIID refiidA = new Guid.REFIID(IUIAutomationElementArray.IID);

        WinNT.HRESULT resultA = unkConditionA.QueryInterface(refiidA, pUnknownA);
        if (COMUtils.SUCCEEDED(resultA)) {
            IUIAutomationElementArray collection =
                    IUIAutomationElementArray.Converter.PointerToInterface(pUnknownA);

            IntByReference ibr = new IntByReference();

            collection.get_Length(ibr);

            int value = ibr.getValue();

            logger.info("Collection length is " + value);

            for (int a = 0; a < value; a++) {
                PointerByReference pbr = new PointerByReference();

                collection.GetElement(a, pbr);

                // Now make a Element out of it

                Unknown uElement = new Unknown(pbr.getValue());

                Guid.REFIID refiidElement = new Guid.REFIID(IUIAutomationElement.IID);

                WinNT.HRESULT result0 = uElement.QueryInterface(refiidElement, pbr);

                if (COMUtils.SUCCEEDED(result0)) {
                    IUIAutomationElement element =
                            IUIAutomationElement.Converter.PointerToInterface(pbr);

                    PointerByReference sr = new PointerByReference();

                    element.get_CurrentClassName(sr);

                    String name = sr.getValue().getWideString(0);

                  //  logger.info("Found: " + name);

                    if (name.equals("Notepad")) {
                        logger.info("Notepad is running");

                        logger.info(name);

                        // Try and get stuff from the notepad

                        // framework
                        PointerByReference sr0 = new PointerByReference();
                        int result = element.get_CurrentFrameworkId(sr0);
                        logger.info(sr0.getValue().getWideString(0));

                        Variant.VARIANT.ByReference ibr0 = new Variant.VARIANT.ByReference();
                        element.get_CurrentPropertyValue(PropertyID.ProcessId.getValue(), ibr0);
                        logger.info(ibr0.getValue());

                        // Is a window pattern available
                        Variant.VARIANT.ByReference ibr1 = new Variant.VARIANT.ByReference();
                        element.get_CurrentPropertyValue(PropertyID.IsWindowPatternAvailable.getValue(), ibr1);

                        // Seems to be no, but not sure why, as it IS a window
                        logger.info(ibr1.getValue());

                        // Is a table pattern available - should be false
                        Variant.VARIANT.ByReference ibr10 = new Variant.VARIANT.ByReference();
                        element.get_CurrentPropertyValue(PropertyID.IsTablePatternAvailable.getValue(), ibr10);

                        // Seems to be no, but not sure why, as it IS a window
                        logger.info(ibr10.getValue());

                        // aria role
                        PointerByReference aria = new PointerByReference();
                        result = element.get_CurrentAriaRole(aria);
                        logger.info(aria.getValue().getWideString(0));

                        // orientation


                    }
                }
            }

        }


    }
}
