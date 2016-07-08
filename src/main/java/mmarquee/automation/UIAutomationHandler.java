package mmarquee.automation;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.*;
import com.sun.jna.platform.win32.COM.COMUtils;
import com.sun.jna.platform.win32.COM.Unknown;
import com.sun.jna.ptr.PointerByReference;

/**
 * Created by inpwt on 26/06/2016.
 */
public class UIAutomationHandler extends Unknown {
    public static final Guid.GUID CLSID_CUIAutomation = new Guid.GUID("{FF48DBA4-60EF-4201-AA87-54103EEF594E}");
    public static final Guid.GUID IID_IUIAutomation = new Guid.GUID("{30CBE57D-D9D0-452A-AB13-7AC5AC4825EE}");

    private UIAutomationHandler(Pointer pvInstance) {
        super(pvInstance);
    }

    public static UIAutomationHandler create() {
        Ole32.INSTANCE.CoInitializeEx(Pointer.NULL, Ole32.COINIT_APARTMENTTHREADED);

        PointerByReference pbr = new PointerByReference();

        WinNT.HRESULT hr = Ole32.INSTANCE.CoCreateInstance(
                CLSID_CUIAutomation,
                null,
                WTypes.CLSCTX_SERVER,
                IID_IUIAutomation,
                pbr);

        COMUtils.checkRC(hr);

        UIAutomationHandler tb = new UIAutomationHandler(pbr.getValue());

        return tb;
    }

    public static void destroy() {
        Ole32.INSTANCE.CoUninitialize();
    }

    private static int UIA_GET_ROOT_ELEMENT = 5;
    private static int UIA_GET_ELEMENT_FROM_HANDLE = 6;
    private static int UIA_GET_FOCUSED_ELEMENT = 8;
    private static int UIA_CREATE_TRUE_CONDITION = 21;
    private static int UIA_CREATE_FALSE_CONDITION = 22;
    private static int UIA_CREATE_PROPERTY_CONDITION = 23;
    private static int UIA_CREATE_AND_CONDITION = 25;
    private static int UIA_CREATE_OR_CONDITION = 28;
    private static int UIA_CREATE_NOT_CONDITION = 31;

    public void GetRootElement(PointerByReference elt) {
        int result = this._invokeNativeInt(UIA_GET_ROOT_ELEMENT, new Object[]{this.getPointer(), elt});
        COMUtils.checkRC(new WinNT.HRESULT(result));
    }

    public void ElementFromHandle(WinDef.HWND hwnd, PointerByReference element) {
        int result = this._invokeNativeInt(UIA_GET_ELEMENT_FROM_HANDLE, new Object[]{this.getPointer(), hwnd, element});
        COMUtils.checkRC(new WinNT.HRESULT(result));
    }

    public void GetFocusedElement(PointerByReference elt) {
        int result = this._invokeNativeInt(UIA_GET_FOCUSED_ELEMENT, new Object[]{this.getPointer(), elt});
        COMUtils.checkRC(new WinNT.HRESULT(result));
    }

    public void CreateTrueCondition(PointerByReference elt) {
        int result = this._invokeNativeInt(UIA_CREATE_TRUE_CONDITION, new Object[]{this.getPointer(), elt});
        COMUtils.checkRC(new WinNT.HRESULT(result));
    }

    public void CreateFalseCondition(PointerByReference elt) {
        int result = this._invokeNativeInt(UIA_CREATE_FALSE_CONDITION, new Object[]{this.getPointer(), elt});
        COMUtils.checkRC(new WinNT.HRESULT(result));
    }

    public void CreateAndCondition(PointerByReference elt0, PointerByReference elt1, PointerByReference elt) {
        int result = this._invokeNativeInt(UIA_CREATE_AND_CONDITION, new Object[]{this.getPointer(), elt0, elt1, elt});
        COMUtils.checkRC(new WinNT.HRESULT(result));
    }

    public void CreateOrCondition(PointerByReference elt0, PointerByReference elt1, PointerByReference elt) {
        int result = this._invokeNativeInt(UIA_CREATE_OR_CONDITION, new Object[]{this.getPointer(), elt0, elt1, elt});
        COMUtils.checkRC(new WinNT.HRESULT(result));
    }

    public void CreatePropertyCondition(int propertyId, Variant value, PointerByReference elt) {
        int result = this._invokeNativeInt(UIA_CREATE_PROPERTY_CONDITION, new Object[]{this.getPointer(), propertyId, value, elt});
        COMUtils.checkRC(new WinNT.HRESULT(result));
    }

    public void CreateNotCondition(PointerByReference elt) {
        int result = this._invokeNativeInt(UIA_CREATE_NOT_CONDITION, new Object[]{this.getPointer(), elt});
        COMUtils.checkRC(new WinNT.HRESULT(result));
    }
/*
    private void ElementFromIAccessible(IAccessible accessible, int childId, PointerByReference elt) {
        int result = this._invokeNativeInt(UIA_CREATE_NOT_CONDITION, new Object[]{this.getPointer(), accessible, childId, elt});
        COMUtils.checkRC(new WinNT.HRESULT(result));
    }
*/
}
