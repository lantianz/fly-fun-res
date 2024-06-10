package com.ltz.flyfun.parser.config;

import com.ltz.flyfun.parser.parserImpl.*;
import com.ltz.flyfun.parser.parserService.ParserInterface;

/**
 * @包名: com.ltz.flyfun.parser.config
 * @类名: ParserInterfaceFactory
 * @描述: 支持站点配置类
 * @日期: 2024/2/5 20:11
 * @版本: 1.0
 */
public class ParserInterfaceFactory {
    // 当前使用的默认的源
    public static int USER_SOURSE = 0;

    public static final int SOURCE_ANFUNS = 0;  // AnFuns动漫
    public static final int SOURCE_TBYS = 1;    // 拖布影视
    public static final int SOURCE_IYINGHUA = 2;    // 樱花动漫
    public static final int SOURCE_LIBVIO = 3;  // LIBVIO
    public static final int SOURCE_ZXZJ = 4;    // 在线之家
    private static volatile ParserInterface parserInterface;

    public static ParserInterface getParserInterface() {
        if (parserInterface == null) {
            switch (USER_SOURSE) {
                case SOURCE_TBYS:
                    parserInterface = new TbysImpl();
                    break;
                case SOURCE_IYINGHUA:
                    parserInterface = new IYingHuaImpl();
                    break;
                case SOURCE_ANFUNS:
                    parserInterface = new AnFunsImpl();
                    break;
                case SOURCE_LIBVIO:
                    parserInterface = new LibvioImpl();
                    break;
                case SOURCE_ZXZJ:
                    parserInterface = new ZxzjImpl();
                    break;
            }
        }
        return parserInterface;
    }
}
