package org.adorsys.adcore.upload;


import java.util.HashMap;

public class FlowInfoStorage {


    //Single instance
    private FlowInfoStorage() {
    }
    private static FlowInfoStorage sInstance;

    public static synchronized FlowInfoStorage getInstance() {
        if (sInstance == null) {
            sInstance = new FlowInfoStorage();
        }
        return sInstance;
    }

    //flowIdentifier --  FlowInfo
    private HashMap<String, FlowInfo> mMap = new HashMap<String, FlowInfo>();

    /**
     * Get FlowInfo from mMap or Create a new one.
     * @param flowChunkSize
     * @param flowTotalSize
     * @param flowIdentifier
     * @param flowFilename
     * @param flowRelativePath
     * @param flowFilePath
     * @param xlsType
     * @return
     */
    public synchronized FlowInfo get(int flowChunkSize, long flowTotalSize,
                             String flowIdentifier, String flowFilename,
                             String flowRelativePath, String flowFilePath, XlsType xlsType) {

        FlowInfo info = mMap.get(flowIdentifier);

        if (info == null) {
            info = new FlowInfo();

            info.flowChunkSize     = flowChunkSize;
            info.flowTotalSize     = flowTotalSize;
            info.flowIdentifier    = flowIdentifier;
            info.flowFilename      = flowFilename;
            info.flowRelativePath  = flowRelativePath;
            info.flowFilePath      = flowFilePath;
            info.xlsType           = xlsType;

            mMap.put(flowIdentifier, info);
        }
        return info;
    }

    /**
     * É¾³ýFlowInfo
     * @param info
     */
    public void remove(FlowInfo info) {
       mMap.remove(info.flowIdentifier);
    }
}