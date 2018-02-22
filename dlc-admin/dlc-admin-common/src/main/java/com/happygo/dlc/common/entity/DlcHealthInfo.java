package com.happygo.dlc.common.entity;

/**
 * ClassName: DlcHealthInfo <br/>
 * Description: DlcHealthInfo <br/>
 * Date: 2017/10/19 14:58 <br/>
 *
 * @author sxp(1378127237@qq.com)<br >
 * @version 1.0 <br/>
 */
public class DlcHealthInfo {
    /**
     * The Status.
     */
    private String status;
    /**
     * The Disk space.
     */
    private DiskSpace diskSpace;
    /**
     * The Db.
     */
    private DB db;

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets disk space.
     *
     * @return the disk space
     */
    public DiskSpace getDiskSpace() {
        return diskSpace;
    }

    /**
     * Sets disk space.
     *
     * @param diskSpace the disk space
     */
    public void setDiskSpace(DiskSpace diskSpace) {
        this.diskSpace = diskSpace;
    }

    /**
     * Gets db.
     *
     * @return the db
     */
    public DB getDb() {
        return db;
    }

    /**
     * Sets db.
     *
     * @param db the db
     */
    public void setDb(DB db) {
        this.db = db;
    }

    /**
     * The type Disk space.
     */
    public static class DiskSpace{
        /**
         * The Status.
         */
        private String status;

        /**
         * The Total.
         */
        private long total;

        /**
         * The Free.
         */
        private long free;

        /**
         * The Threshold.
         */
        private long threshold;

        /**
         * Gets status.
         *
         * @return the status
         */
        public String getStatus() {
            return status;
        }

        /**
         * Sets status.
         *
         * @param status the status
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * Gets total.
         *
         * @return the total
         */
        public long getTotal() {
            return total;
        }

        /**
         * Sets total.
         *
         * @param total the total
         */
        public void setTotal(long total) {
            this.total = total;
        }

        /**
         * Gets free.
         *
         * @return the free
         */
        public long getFree() {
            return free;
        }

        /**
         * Sets free.
         *
         * @param free the free
         */
        public void setFree(long free) {
            this.free = free;
        }

        /**
         * Gets threshold.
         *
         * @return the threshold
         */
        public long getThreshold() {
            return threshold;
        }

        /**
         * Sets threshold.
         *
         * @param threshold the threshold
         */
        public void setThreshold(long threshold) {
            this.threshold = threshold;
        }
    }

    /**
     * The type Db.
     */
    public static class DB{
        /**
         * The Status.
         */
        private String status;

        /**
         * The Database.
         */
        private String database;

        /**
         * Gets status.
         *
         * @return the status
         */
        public String getStatus() {
            return status;
        }

        /**
         * Sets status.
         *
         * @param status the status
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * Gets database.
         *
         * @return the database
         */
        public String getDatabase() {
            return database;
        }

        /**
         * Sets database.
         *
         * @param database the database
         */
        public void setDatabase(String database) {
            this.database = database;
        }
    }
}