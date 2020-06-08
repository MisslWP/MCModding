package ru.uncledrema.additionalitems.inventoryitems;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;

public class FunLogger {
    
     public static void log(Level logLevel, Object object)
        {
            FMLLog.log("inventoryitemmod", logLevel, String.valueOf(object));
        }

         public static void all(Object object)
        {
            log(Level.ALL, object);
        }

        public static void debug(Object object)
        {
            log(Level.DEBUG, object);
        }

        public static void error(Object object) //ошибка
        {
            log(Level.ERROR, object);
        }

        public static void fatal(Object object) //критическая ошибка
        {
            log(Level.FATAL, object);
        }

        public static void info(Object object) // информация
        {
            log(Level.INFO, object);
        }

        public static void off(Object object)
        {
            log(Level.OFF, object);
        }

        public static void trace(Object object)
        {
            log(Level.TRACE, object);
        }

        public static void warn(Object object) //предупреждение
        {
            log(Level.WARN, object);
        }
}
