import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;


// 存储神器属性的映射表（神器名称 -> 属性数组[攻击,防御,生命,智力,精力]）
Map itemAttrMap = new HashMap();
// 基础神器
itemAttrMap.put("太虚镜", new long[]{5000000, 3000000, 50000000, 10000, 25000});
itemAttrMap.put("青冥剑", new long[]{6500000, 2500000, 45000000, 10000, 25000});
itemAttrMap.put("玄冰盾", new long[]{3000000, 8000000, 30000000, 10000, 25000});
itemAttrMap.put("焚天印", new long[]{5000000, 3000000, 50000000, 10000, 25000});
itemAttrMap.put("星陨珠", new long[]{5000000, 3000000, 50000000, 10000, 25000});
itemAttrMap.put("镇魔幡", new long[]{5000000, 3000000, 50000000, 10000, 25000});
itemAttrMap.put("聚灵盏", new long[]{4000000, 3000000, 40000000, 10000, 25000});
// 龙珠体系神器
itemAttrMap.put("聚气青锋", new long[]{2000, 1500, 50000, 100, 150});
itemAttrMap.put("旋影游龙剑", new long[]{4500, 3200, 95000, 100, 250});
itemAttrMap.put("龙啸破云刃", new long[]{7000, 5500, 130000, 100, 400});
itemAttrMap.put("裂空星陨剑", new long[]{10000, 8000, 165000, 100, 750});
itemAttrMap.put("陨星龙脊剑", new long[]{25000, 12500, 340000, 100, 1000});
itemAttrMap.put("九霄龙吟剑", new long[]{500000, 450000, 1150000, 500, 2000});
itemAttrMap.put("混沌盘龙刃", new long[]{1100000, 950000, 2500000, 500, 5000});
itemAttrMap.put("万象龙纹戟", new long[]{3000000, 1750000, 5000000, 500, 10000});
itemAttrMap.put("太虚龙魄枪", new long[]{5000000, 4250000, 12000000, 500, 15000});
itemAttrMap.put("焚世龙炎刀", new long[]{12500000, 10500000, 26500000, 500, 20000});
itemAttrMap.put("镇狱龙渊斧", new long[]{20500000, 18350000, 42220000, 10000, 25000});
itemAttrMap.put("永恒龙晶锏", new long[]{40000000, 35000000, 85000000, 10000, 30000});
itemAttrMap.put("混沌祖龙杵", new long[]{46500000, 40000000, 100000000, 1000000, 35000});
itemAttrMap.put("造化天龙钩", new long[]{54000000, 47500000, 165000000, 5000000, 40000});
itemAttrMap.put("无上龙尊槊", new long[]{63000000, 58600000, 247000000, 10000000, 45000});
itemAttrMap.put("混元终焉剑", new long[]{90000000, 80000000, 300000000, 20000000, 500000});
// “星核”体系神器
itemAttrMap.put("星辉凝霜剑", new long[]{1800, 1300, 45000, 100, 120});
itemAttrMap.put("幻星逐月双剑", new long[]{4000, 2800, 85000, 200, 200});
itemAttrMap.put("星痕破军剑", new long[]{6500, 5000, 120000, 350, 350});
itemAttrMap.put("流光星陨刃", new long[]{9000, 7500, 155000, 500, 650});
itemAttrMap.put("陨星破晓剑", new long[]{23000, 11500, 320000, 750, 900});
itemAttrMap.put("星辰耀世枪", new long[]{450000, 400000, 1050000, 1500, 1800});
itemAttrMap.put("星穹龙脊棍", new long[]{1000000, 850000, 2200000, 2500, 4500});
itemAttrMap.put("星渊万象戟", new long[]{2800000, 1600000, 4800000, 4000, 9000});
itemAttrMap.put("太虚星魄杖", new long[]{4800000, 4000000, 11000000, 6000, 13500});
itemAttrMap.put("焚星龙炎鞭", new long[]{12000000, 10000000, 25000000, 8000, 18000});
itemAttrMap.put("镇狱星渊斧", new long[]{20000000, 18000000, 40000000, 10000, 22500});
itemAttrMap.put("永恒星晶锤", new long[]{38000000, 33000000, 82000000, 12000, 27000});
itemAttrMap.put("混沌星祖锏", new long[]{45000000, 38000000, 98000000, 1000000, 31500});
itemAttrMap.put("造化星龙枪", new long[]{52000000, 46000000, 160000000, 5000000, 36000});
itemAttrMap.put("无上星尊剑", new long[]{61000000, 57000000, 240000000, 10000000, 40500});
itemAttrMap.put("混元星穹刃", new long[]{88000000, 78000000, 295000000, 20000000, 450000});

// 玄铁体系（锤系）神器
itemAttrMap.put("淬体玄铁槌", new long[]{1800, 1800, 48000, 80, 120});
itemAttrMap.put("裂岩陨铁锤", new long[]{4000, 3800, 92000, 80, 220});
itemAttrMap.put("撼地千钧锤", new long[]{6500, 6000, 125000, 80, 350});
itemAttrMap.put("破天碎岳锤", new long[]{9000, 7500, 160000, 80, 600});
itemAttrMap.put("碎空陨星锤", new long[]{22000, 11500, 320000, 80, 900});
itemAttrMap.put("震岳凌霄锤", new long[]{480000, 420000, 1100000, 400, 1800});
itemAttrMap.put("幽冥鬼煞锤", new long[]{1050000, 900000, 2400000, 400, 4500});
itemAttrMap.put("狂澜镇海锤", new long[]{2800000, 1650000, 4800000, 400, 9000});
itemAttrMap.put("霸世吞天锤", new long[]{4800000, 4000000, 11500000, 400, 14000});
itemAttrMap.put("怒焰焚天锤", new long[]{12000000, 10000000, 25500000, 400, 19000});
itemAttrMap.put("灭世混沌锤", new long[]{20000000, 18000000, 41800000, 9000, 24000});
itemAttrMap.put("永恒不朽锤", new long[]{38000000, 33000000, 82000000, 9000, 28000});
itemAttrMap.put("混沌祖神锤", new long[]{45000000, 38000000, 98000000, 1000000, 33000});
itemAttrMap.put("造化圣天锤", new long[]{52000000, 46000000, 160000000, 5000000, 38000});
itemAttrMap.put("无上道尊锤", new long[]{61000000, 57000000, 240000000, 10000000, 43000});
itemAttrMap.put("混元终焉锤", new long[]{88000000, 78000000, 295000000, 20000000, 480000});
// “灵藤”体系神器
itemAttrMap.put("缠枝灵藤鞭", new long[]{1600, 1200, 45000, 120, 180});
itemAttrMap.put("疾风掠影鞭", new long[]{3800, 2800, 90000, 120, 280});
itemAttrMap.put("幻影迷踪鞭", new long[]{6300, 4800, 120000, 120, 450});
itemAttrMap.put("流光幻影鞭", new long[]{8800, 7000, 155000, 120, 800});
itemAttrMap.put("幽影噬魂鞭", new long[]{20000, 11000, 300000, 120, 1100});
itemAttrMap.put("青冥九霄鞭", new long[]{460000, 400000, 1120000, 600, 2200});
itemAttrMap.put("幽冥黄泉鞭", new long[]{1000000, 920000, 2350000, 600, 5200});
itemAttrMap.put("灵韵万法鞭", new long[]{2700000, 1600000, 4700000, 600, 9500});
itemAttrMap.put("天影追魂鞭", new long[]{4600000, 4000000, 11000000, 600, 13500});
itemAttrMap.put("御灵控魔鞭", new long[]{11800000, 9800000, 25000000, 600, 18500});
itemAttrMap.put("幻世浮生鞭", new long[]{19800000, 17800000, 41500000, 11000, 23500});
itemAttrMap.put("永恒命魂鞭", new long[]{38500000, 32500000, 80000000, 11000, 27500});
itemAttrMap.put("混沌太初鞭", new long[]{45500000, 38500000, 96000000, 1000000, 32500});
itemAttrMap.put("造化阴阳鞭", new long[]{53000000, 46000000, 158000000, 5000000, 37500});
itemAttrMap.put("无上乾坤鞭", new long[]{62000000, 57000000, 238000000, 10000000, 42500});
itemAttrMap.put("混元无极鞭", new long[]{87000000, 77000000, 292000000, 20000000, 475000});

// 合成信息辅助类（支持多材料）
class SynthesisInfo {
    // 前置道具材料（材料名称 -> 所需数量）
    Map materialMap = new HashMap();
    // 基础资源材料（材料名称 -> 所需数量）
    Map altMaterialMap = new HashMap();
}

// 工具方法：从背包扣除道具
void deductItem(String bagKey, String item, long count) {
    if (item == null || "".equals(item) || count <= 0) {
        return;
    }
    long itemCount = 文转(getString(bagKey, item));
    long 剩余数量 = itemCount - count;
    
    if (剩余数量 >= 1) {
        putString(bagKey, item, 转文(剩余数量));
    } else {
        String bag = getString(bagKey, "道具列表");
        if (bag == null) {
            return;
        }
        // 移除道具列表中的目标道具
        String newBag = bag.replace("、" + item, "").replace(item + "、", "").replace(item, "");
        if (newBag.trim().isEmpty() || "null".equals(newBag.trim().toLowerCase())) {
            putString(bagKey, "道具列表", null);
            putString(bagKey, item, null);
        } else {
            putString(bagKey, "道具列表", newBag);
            putString(bagKey, item, null);
        }
    }
}

// 工具方法：添加道具到背包（修复版，兼容多材料添加）
String addItemToBagCompat(String bagKey, String item, long count, String currentBag) {
    if (item == null || "".equals(item.trim()) || count <= 0) {
        return currentBag; // 直接返回原列表
    }
    String itemTrimmed = item.trim();
    boolean exists = false;

    // 判断道具是否已在当前列表中（原始循环，无Lambda）
    if (!currentBag.isEmpty()) {
        String[] items = currentBag.split("、");
        for (int i = 0; i < items.length; i++) {
            if (itemTrimmed.equals(items[i].trim())) {
                exists = true;
                break;
            }
        }
    }

    if (exists) {
        // 已存在：累加数量
        long currCount = 文转(getString(bagKey, itemTrimmed));
        putString(bagKey, itemTrimmed, 转文(currCount + count));
        return currentBag; // 列表不变，直接返回
    } else {
        // 不存在：添加到列表
        String newBag = currentBag.isEmpty() ? itemTrimmed : currentBag + "、" + itemTrimmed;
        putString(bagKey, "道具列表", newBag);
        putString(bagKey, itemTrimmed, 转文(count));
        return newBag; // 返回更新后的列表
    }
}

// 基础神器合成配置补充
void initBasicSynthesis(SynthesisInfo info, String item) {
    if ("青冥剑".equals(item)) {
        info.altMaterialMap.put("青冥玄铁", 1L);
        info.altMaterialMap.put("天青藤", 1L);
        info.altMaterialMap.put("剑灵残魂", 1L);
    } else if ("聚灵盏".equals(item)) {
        info.altMaterialMap.put("聚灵玉髓", 1L);
        info.altMaterialMap.put("日月精华石", 1L);
        info.altMaterialMap.put("灵木心", 1L);
    } else if ("镇魔幡".equals(item)) {
        info.altMaterialMap.put("镇魔符箓", 1L);
        info.altMaterialMap.put("九幽魔藤", 1L);
        info.altMaterialMap.put("魔王精血", 1L);
    } else if ("星陨珠".equals(item)) {
        info.altMaterialMap.put("陨星碎片", 1L);
        info.altMaterialMap.put("星辉石", 1L);
        info.altMaterialMap.put("星辰沙", 1L);
    } else if ("焚天印".equals(item)) {
        info.altMaterialMap.put("焚天炎晶", 1L);
        info.altMaterialMap.put("赤阳火精", 1L);
        info.altMaterialMap.put("炎魔之心", 1L);
    } else if ("玄冰盾".equals(item)) {
        info.altMaterialMap.put("玄冰髓", 1L);
        info.altMaterialMap.put("极地寒晶", 1L);
        info.altMaterialMap.put("冰魄之心", 1L);
    } else if ("太虚镜".equals(item)) {
        info.altMaterialMap.put("太虚镜碎片", 1L);
        info.altMaterialMap.put("凝神丹", 1L); 
    }
}

// 龙珠体系神器合成配置补充
void initDragonBallSynthesis(SynthesisInfo info, String item) {
    if ("聚气青锋".equals(item)) {
        info.altMaterialMap.put("龙珠", 5L);
    } else if ("旋影游龙剑".equals(item)) {
        info.materialMap.put("聚气青锋", 2L);
        info.altMaterialMap.put("龙珠", 10L);
    } else if ("龙啸破云刃".equals(item)) {
        info.materialMap.put("旋影游龙剑", 2L);
        info.altMaterialMap.put("龙珠", 20L);
    } else if ("裂空星陨剑".equals(item)) {
        info.materialMap.put("龙啸破云刃", 2L);
        info.altMaterialMap.put("龙珠", 40L);
    } else if ("陨星龙脊剑".equals(item)) {
        info.materialMap.put("裂空星陨剑", 2L);
        info.altMaterialMap.put("龙珠", 80L);
    } else if ("九霄龙吟剑".equals(item)) {
        info.materialMap.put("陨星龙脊剑", 2L);
        info.altMaterialMap.put("龙珠", 160L);
    } else if ("混沌盘龙刃".equals(item)) {
        info.materialMap.put("九霄龙吟剑", 2L);
        info.altMaterialMap.put("龙珠", 320L);
    } else if ("万象龙纹戟".equals(item)) {
        info.materialMap.put("混沌盘龙刃", 2L);
        info.altMaterialMap.put("龙珠", 640L);
    } else if ("太虚龙魄枪".equals(item)) {
        info.materialMap.put("万象龙纹戟", 2L);
        info.altMaterialMap.put("龙珠", 1280L);
    } else if ("焚世龙炎刀".equals(item)) {
        info.materialMap.put("太虚龙魄枪", 2L);
        info.altMaterialMap.put("龙珠", 2560L);
    } else if ("镇狱龙渊斧".equals(item)) {
        info.materialMap.put("焚世龙炎刀", 2L);
        info.altMaterialMap.put("龙珠", 5120L);
    } else if ("永恒龙晶锏".equals(item)) {
        info.materialMap.put("镇狱龙渊斧", 2L);
        info.altMaterialMap.put("龙珠", 10240L);
    } else if ("混沌祖龙杵".equals(item)) {
        info.materialMap.put("永恒龙晶锏", 2L);
        info.altMaterialMap.put("龙珠", 15000L);
    } else if ("造化天龙钩".equals(item)) {
        info.materialMap.put("混沌祖龙杵", 2L);
        info.altMaterialMap.put("龙珠", 20000L);
    } else if ("无上龙尊槊".equals(item)) {
        info.materialMap.put("造化天龙钩", 2L);
        info.altMaterialMap.put("龙珠", 25000L);
    } else if ("混元终焉剑".equals(item)) {
        info.materialMap.put("无上龙尊槊", 2L);
        info.altMaterialMap.put("龙珠", 30000L);
    }
}

// 星核体系神器合成配置补充
void initStarCoreSynthesis(SynthesisInfo info, String item) {
    if ("星辉凝霜剑".equals(item)) {
        info.altMaterialMap.put("星核", 5L);
    } else if ("幻星逐月双剑".equals(item)) {
        info.materialMap.put("星辉凝霜剑", 2L);
        info.altMaterialMap.put("星核", 10L);
    } else if ("星痕破军剑".equals(item)) {
        info.materialMap.put("幻星逐月双剑", 2L);
        info.altMaterialMap.put("星核", 20L);
    } else if ("流光星陨刃".equals(item)) {
        info.materialMap.put("星痕破军剑", 2L);
        info.altMaterialMap.put("星核", 40L);
    } else if ("陨星破晓剑".equals(item)) {
        info.materialMap.put("流光星陨刃", 2L);
        info.altMaterialMap.put("星核", 80L);
    } else if ("星辰耀世枪".equals(item)) {
        info.materialMap.put("陨星破晓剑", 2L);
        info.altMaterialMap.put("星核", 160L);
    } else if ("星穹龙脊棍".equals(item)) {
        info.materialMap.put("星辰耀世枪", 2L);
        info.altMaterialMap.put("星核", 320L);
    } else if ("星渊万象戟".equals(item)) {
        info.materialMap.put("星穹龙脊棍", 2L);
        info.altMaterialMap.put("星核", 640L);
    } else if ("太虚星魄杖".equals(item)) {
        info.materialMap.put("星渊万象戟", 2L);
        info.altMaterialMap.put("星核", 1280L);
    } else if ("焚星龙炎鞭".equals(item)) {
        info.materialMap.put("太虚星魄杖", 2L);
        info.altMaterialMap.put("星核", 2560L);
    } else if ("镇狱星渊斧".equals(item)) {
        info.materialMap.put("焚星龙炎鞭", 2L);
        info.altMaterialMap.put("星核", 5120L);
    } else if ("永恒星晶锤".equals(item)) {
        info.materialMap.put("镇狱星渊斧", 2L);
        info.altMaterialMap.put("星核", 10240L);
    } else if ("混沌星祖锏".equals(item)) {
        info.materialMap.put("永恒星晶锤", 2L);
        info.altMaterialMap.put("星核", 15000L);
    } else if ("造化星龙枪".equals(item)) {
        info.materialMap.put("混沌星祖锏", 2L);
        info.altMaterialMap.put("星核", 20000L);
    } else if ("无上星尊剑".equals(item)) {
        info.materialMap.put("造化星龙枪", 2L);
        info.altMaterialMap.put("星核", 25000L);
    } else if ("混元星穹刃".equals(item)) {
        info.materialMap.put("无上星尊剑", 2L);
        info.altMaterialMap.put("星核", 30000L);
    }
}

// 玄铁体系（锤系）神器合成配置补充
void initBlackIronSynthesis(SynthesisInfo info, String item) {
    if ("淬体玄铁槌".equals(item)) {
        info.altMaterialMap.put("玄铁", 5L);
    } else if ("裂岩陨铁锤".equals(item)) {
        info.materialMap.put("淬体玄铁槌", 2L);
        info.altMaterialMap.put("玄铁", 10L);
    } else if ("撼地千钧锤".equals(item)) {
        info.materialMap.put("裂岩陨铁锤", 2L);
        info.altMaterialMap.put("玄铁", 20L);
    } else if ("破天碎岳锤".equals(item)) {
        info.materialMap.put("撼地千钧锤", 2L);
        info.altMaterialMap.put("玄铁", 40L);
    } else if ("碎空陨星锤".equals(item)) {
        info.materialMap.put("破天碎岳锤", 2L);
        info.altMaterialMap.put("玄铁", 80L);
    } else if ("震岳凌霄锤".equals(item)) {
        info.materialMap.put("碎空陨星锤", 2L);
        info.altMaterialMap.put("玄铁", 160L);
    } else if ("幽冥鬼煞锤".equals(item)) {
        info.materialMap.put("震岳凌霄锤", 2L);
        info.altMaterialMap.put("玄铁", 320L);
    } else if ("狂澜镇海锤".equals(item)) {
        info.materialMap.put("幽冥鬼煞锤", 2L);
        info.altMaterialMap.put("玄铁", 640L);
    } else if ("霸世吞天锤".equals(item)) {
        info.materialMap.put("狂澜镇海锤", 2L);
        info.altMaterialMap.put("玄铁", 1280L);
    } else if ("怒焰焚天锤".equals(item)) {
        info.materialMap.put("霸世吞天锤", 2L);
        info.altMaterialMap.put("玄铁", 2560L);
    } else if ("灭世混沌锤".equals(item)) {
        info.materialMap.put("怒焰焚天锤", 2L);
        info.altMaterialMap.put("玄铁", 5120L);
    } else if ("永恒不朽锤".equals(item)) {
        info.materialMap.put("灭世混沌锤", 2L);
        info.altMaterialMap.put("玄铁", 10240L);
    } else if ("混沌祖神锤".equals(item)) {
        info.materialMap.put("永恒不朽锤", 2L);
        info.altMaterialMap.put("玄铁", 15000L);
    } else if ("造化圣天锤".equals(item)) {
        info.materialMap.put("混沌祖神锤", 2L);
        info.altMaterialMap.put("玄铁", 20000L);
    } else if ("无上道尊锤".equals(item)) {
        info.materialMap.put("造化圣天锤", 2L);
        info.altMaterialMap.put("玄铁", 25000L);
    } else if ("混元终焉锤".equals(item)) {
        info.materialMap.put("无上道尊锤", 2L);
        info.altMaterialMap.put("玄铁", 30000L);
    }
}

// 灵藤体系神器合成配置补充
void initSpiritVineSynthesis(SynthesisInfo info, String item) {
    if ("缠枝灵藤鞭".equals(item)) {
        info.altMaterialMap.put("灵藤", 5L);
    } else if ("疾风掠影鞭".equals(item)) {
        info.materialMap.put("缠枝灵藤鞭", 2L);
        info.altMaterialMap.put("灵藤", 10L);
    } else if ("幻影迷踪鞭".equals(item)) {
        info.materialMap.put("疾风掠影鞭", 2L);
        info.altMaterialMap.put("灵藤", 20L);
    } else if ("流光幻影鞭".equals(item)) {
        info.materialMap.put("幻影迷踪鞭", 2L);
        info.altMaterialMap.put("灵藤", 40L);
    } else if ("幽影噬魂鞭".equals(item)) {
        info.materialMap.put("流光幻影鞭", 2L);
        info.altMaterialMap.put("灵藤", 80L);
    } else if ("青冥九霄鞭".equals(item)) {
        info.materialMap.put("幽影噬魂鞭", 2L);
        info.altMaterialMap.put("灵藤", 160L);
    } else if ("幽冥黄泉鞭".equals(item)) {
        info.materialMap.put("青冥九霄鞭", 2L);
        info.altMaterialMap.put("灵藤", 320L);
    } else if ("灵韵万法鞭".equals(item)) {
        info.materialMap.put("幽冥黄泉鞭", 2L);
        info.altMaterialMap.put("灵藤", 640L);
    } else if ("天影追魂鞭".equals(item)) {
        info.materialMap.put("灵韵万法鞭", 2L);
        info.altMaterialMap.put("灵藤", 1280L);
    } else if ("御灵控魔鞭".equals(item)) {
        info.materialMap.put("天影追魂鞭", 2L);
        info.altMaterialMap.put("灵藤", 2560L);
    } else if ("幻世浮生鞭".equals(item)) {
        info.materialMap.put("御灵控魔鞭", 2L);
        info.altMaterialMap.put("灵藤", 5120L);
    } else if ("永恒命魂鞭".equals(item)) {
        info.materialMap.put("幻世浮生鞭", 2L);
        info.altMaterialMap.put("灵藤", 10240L);
    } else if ("混沌太初鞭".equals(item)) {
        info.materialMap.put("永恒命魂鞭", 2L);
        info.altMaterialMap.put("灵藤", 15000L);
    } else if ("造化阴阳鞭".equals(item)) {
        info.materialMap.put("混沌太初鞭", 2L);
        info.altMaterialMap.put("灵藤", 20000L);
    } else if ("无上乾坤鞭".equals(item)) {
        info.materialMap.put("造化阴阳鞭", 2L);
        info.altMaterialMap.put("灵藤", 25000L);
    } else if ("混元无极鞭".equals(item)) {
        info.materialMap.put("无上乾坤鞭", 2L);
        info.altMaterialMap.put("灵藤", 30000L);
    }
}

// 合成配方，整合所有体系
SynthesisInfo getSynthesisInfo(String item) {
    if (item == null || "".equals(item.trim())) {
        return null;
    }
    SynthesisInfo info = new SynthesisInfo();
    
    // 1. 基础神器合成配置
    initBasicSynthesis(info, item);
    if (!info.materialMap.isEmpty() || !info.altMaterialMap.isEmpty()) {
        return info;
    }
    
    // 2. 龙珠体系合成配置
    initDragonBallSynthesis(info, item);
    if (!info.materialMap.isEmpty() || !info.altMaterialMap.isEmpty()) {
        return info;
    }
    
    // 3. 星核体系合成配置
    initStarCoreSynthesis(info, item);
    if (!info.materialMap.isEmpty() || !info.altMaterialMap.isEmpty()) {
        return info;
    }
    
    // 4. 玄铁体系合成配置
    initBlackIronSynthesis(info, item);
    if (!info.materialMap.isEmpty() || !info.altMaterialMap.isEmpty()) {
        return info;
    }
    
    // 5. 灵藤体系合成配置
    initSpiritVineSynthesis(info, item);
    if (!info.materialMap.isEmpty() || !info.altMaterialMap.isEmpty()) {
        return info;
    }
    
    // 未匹配到任何合成配方
    return null;
}


// 合成神器（优先消耗上一级道具，不足时用基础材料）
void onSynthesize(String qun, String uin, String targetItem, long 合成数量) {
    // 参数校验
    if (targetItem == null || "".equals(targetItem.trim())) {
        发送(qun, "[AtQQ=" + uin + "] 合成失败：请指定要合成的道具", true);
        return;
    }
    if (合成数量 <= 0) {
        发送(qun, "[AtQQ=" + uin + "] 合成失败：合成数量必须大于0", true);
        return;
    }
    String bagKey = qun + "/" + uin + "/我的背包";
    String bag = getString(bagKey, "道具列表");
    SynthesisInfo info = getSynthesisInfo(targetItem);
    // 检查合成配方
    if (info == null) {
        发送(qun, "[AtQQ=" + uin + "] 合成失败：不存在【" + targetItem + "】的合成配方", true);
        return;
    }
    // 优先检查并消耗上一级道具
    boolean 上级材料充足 = false;
    if (!info.materialMap.isEmpty()) {
        // 遍历前置道具材料（仅支持单种上级道具）
        String 上级道具 = null;
        long 单次需求上级 = 0;
        Iterator iter = info.materialMap.keySet().iterator();
        if (iter.hasNext()) {
            上级道具 = (String) iter.next();
            单次需求上级 = 文转(info.materialMap.get(上级道具).toString());
        }
        if (上级道具 != null && 单次需求上级 > 0) {
            long 总需求上级 = 单次需求上级 * 合成数量;
            long 已有上级数量 = 文转(getString(bagKey, 上级道具));
            if (已有上级数量 >= 总需求上级) {
                // 上级道具充足，直接消耗
                deductItem(bagKey, 上级道具, 总需求上级);
                addItemToBagCompat(bagKey, targetItem, 合成数量, bag);
                发送(qun, "[AtQQ=" + uin + "] 成功合成【" + targetItem + "】×" + 合成数量 + "\n消耗：" + 上级道具 + "×" + 总需求上级, true);
                return;
            }
        }
    }
    // 上级道具不足，检查基础材料
    StringBuilder 材料缺口 = new StringBuilder();
    boolean 基础材料充足 = true;
    if (!info.altMaterialMap.isEmpty()) {
        Iterator iter = info.altMaterialMap.keySet().iterator();
        while (iter.hasNext()) {
            String 基础材料 = (String) iter.next();
            long 单次需求基础 = 文转(info.altMaterialMap.get(基础材料).toString());
            long 总需求基础 = 单次需求基础 * 合成数量;
            long 已有基础数量 = 文转(getString(bagKey, 基础材料));
            if (已有基础数量 < 总需求基础) {
                基础材料充足 = false;
                材料缺口.append("•" + 基础材料 + "：需" + 总需求基础 + "，现有" + 已有基础数量 + "\n");
            }
        }
    }
    // 基础材料不足提示
    if (!基础材料充足) {
        发送(qun, "[AtQQ=" + uin + "] 合成失败：材料不足\n" + 材料缺口.toString(), true);
        return;
    }
    // 消耗基础材料并合成
    StringBuilder 消耗信息 = new StringBuilder();
    消耗信息.append("\n消耗基础材料：\n");
    Iterator iter = info.altMaterialMap.keySet().iterator();
    while (iter.hasNext()) {
        String 基础材料 = (String) iter.next();
        long 单次需求基础 = 文转(info.altMaterialMap.get(基础材料).toString());
        long 总需求基础 = 单次需求基础 * 合成数量;
        deductItem(bagKey, 基础材料, 总需求基础);
        消耗信息.append("•" + 基础材料 + "×" + 总需求基础 + "\n");
    }
    addItemToBagCompat(bagKey, targetItem, 合成数量, bag);
    发送(qun, "[AtQQ=" + uin + "] 成功合成【" + targetItem + "】×" + 合成数量 + "\n" + 消耗信息.toString(), true);
}


// 分解神器（修复多材料添加问题）
void onDecompose(String qun, String uin, String targetItem, long 分解数量) {
    // 参数校验
    if (targetItem == null || "".equals(targetItem.trim())) {
        发送(qun, "[AtQQ=" + uin + "] 分解失败：请指定要分解的道具", true);
        return;
    }
    if (分解数量 <= 0) {
        发送(qun, "[AtQQ=" + uin + "] 分解失败：分解数量必须大于0", true);
        return;
    }
    
    String bagKey = qun + "/" + uin + "/我的背包";
    String bag = getString(bagKey, "道具列表");
    
    // 检查背包是否存在该道具
    if (bag == null || !bag.contains(targetItem)) {
        发送(qun, "[AtQQ=" + uin + "] 分解失败：背包中没有【" + targetItem + "】", true);
        return;
    }
    
    // 检查数量是否充足
    long 持有数量 = 文转(getString(bagKey, targetItem));
    if (持有数量 < 分解数量) {
        发送(qun, "[AtQQ=" + uin + "] 分解失败：持有【" + targetItem + "】" + 持有数量 + "个，不足" + 分解数量 + "个", true);
        return;
    }
    
    // 检查是否可分解
    SynthesisInfo info = getSynthesisInfo(targetItem);
    if (info == null) {
        发送(qun, "[AtQQ=" + uin + "] 分解失败：【" + targetItem + "】无法分解（无合成配方）", true);
        return;
    }
    
    // 扣除被分解的道具
    deductItem(bagKey, targetItem, 分解数量);
    
    // 返还分解材料并获取返还信息（调用修复后的方法，返回材料明细）
    String 返还材料信息 = returnMaterials(bagKey, info, 分解数量);
    
    // 发送成功消息，包含返还材料及数量
    发送(qun, "[AtQQ=" + uin + "] 成功分解【" + targetItem + "】×" + 分解数量 + "\n返还材料：\n" + 返还材料信息, true);
}

// 返还分解材料（修复多材料添加逻辑，返回材料明细）
String returnMaterials(String bagKey, SynthesisInfo info, long 分解数量) {
    // 1. 收集所有需添加的材料及数量
    HashMap materialsToAdd = new HashMap();
    // 用于记录返还材料信息
    StringBuilder 返还信息 = new StringBuilder();
    
    // 收集前置道具材料
    if (!info.materialMap.isEmpty()) {
        Iterator iter = info.materialMap.keySet().iterator();
        while (iter.hasNext()) {
            String material = (String) iter.next();
            long 单次返还 = 文转(info.materialMap.get(material).toString());
            long 总返还 = 单次返还 * 分解数量;
            // 累加同类型材料数量
            if (materialsToAdd.containsKey(material)) {
                long existing = 文转(materialsToAdd.get(material).toString());
                materialsToAdd.put(material, 转文(existing + 总返还));
            } else {
                materialsToAdd.put(material, 转文(总返还));
            }
            // 记录返还信息
            返还信息.append("•" + material + "×" + 总返还 + "\n");
        }
    }
    // 收集基础资源材料
    else if (!info.altMaterialMap.isEmpty()) {
        Iterator iter = info.altMaterialMap.keySet().iterator();
        while (iter.hasNext()) {
            String material = (String) iter.next();
            long 单次返还 = 文转(info.altMaterialMap.get(material).toString());
            long 总返还 = 单次返还 * 分解数量;
            if (materialsToAdd.containsKey(material)) {
                long existing = 文转(materialsToAdd.get(material).toString());
                materialsToAdd.put(material, 转文(existing + 总返还));
            } else {
                materialsToAdd.put(material, 转文(总返还));
            }
            // 记录返还信息
            返还信息.append("•" + material + "×" + 总返还 + "\n");
        }
    }
    
    // 2. 逐个添加材料到背包，实时同步道具列表
    String currentBag = getString(bagKey, "道具列表");
    if (currentBag == null || "null".equalsIgnoreCase(currentBag.trim())) {
        currentBag = "";
    }
    Iterator matIter = materialsToAdd.keySet().iterator();
    while (matIter.hasNext()) {
        String material = (String) matIter.next();
        long count = 文转(materialsToAdd.get(material).toString());
        // 调用兼容版添加方法，更新列表
        currentBag = addItemToBagCompat(bagKey, material, count, currentBag);
    }
    
    // 返回返还材料信息
    return 返还信息.toString();
}


// 检查材料是否充足
boolean checkMaterials(String bagKey, SynthesisInfo info, long 合成数量) {
    // 检查前置道具材料
    if (!info.materialMap.isEmpty()) {
        Iterator iter = info.materialMap.keySet().iterator();
        while (iter.hasNext()) {
            String material = (String) iter.next();
            long 单次需求 = 文转(info.materialMap.get(material).toString());
            long 总需求 = 单次需求 * 合成数量;
            long 已有数量 = 文转(getString(bagKey, material));
            if (已有数量 < 总需求) {
                return false;
            }
        }
        return true;
    }
    // 检查基础资源材料
    if (!info.altMaterialMap.isEmpty()) {
        Iterator iter = info.altMaterialMap.keySet().iterator();
        while (iter.hasNext()) {
            String material = (String) iter.next();
            long 单次需求 = 文转(info.altMaterialMap.get(material).toString());
            long 总需求 = 单次需求 * 合成数量;
            long 已有数量 = 文转(getString(bagKey, material));
            if (已有数量 < 总需求) {
                return false;
            }
        }
        return true;
    }
    return false;
}

// 扣除合成材料
void deductMaterials(String bagKey, SynthesisInfo info, long 合成数量) {
    // 优先扣除前置道具材料
    if (!info.materialMap.isEmpty()) {
        Iterator iter = info.materialMap.keySet().iterator();
        while (iter.hasNext()) {
            String material = (String) iter.next();
            long 单次需求 = 文转(info.materialMap.get(material).toString());
            long 总需求 = 单次需求 * 合成数量;
            deductItem(bagKey, material, 总需求);
        }
        return;
    }
    // 扣除基础资源材料
    if (!info.altMaterialMap.isEmpty()) {
        Iterator iter = info.altMaterialMap.keySet().iterator();
        while (iter.hasNext()) {
            String material = (String) iter.next();
            long 单次需求 = 文转(info.altMaterialMap.get(material).toString());
            long 总需求 = 单次需求 * 合成数量;
            deductItem(bagKey, material, 总需求);
        }
    }
}


// 处理神器穿戴时增加的属性
public void 神器穿戴(String qun, String uin, String item) {
    String 配置名 = qun + "/" + uin + "/";  
    String 配置名称 = 配置名 + "/宠物小窝/位置_0";
    String 宠物名 = getString(配置名称, "昵称");
    String bagKey = qun + "/" + uin + "/我的背包";
    String bag = getString(bagKey, "道具列表");
     
    // 出战宠物相关属性
    String 昵称 = getString(配置名称, "昵称");
    String 名字 = getString(配置名称, "宠物名字");
    String 性别 = getString(配置名称, "性别");
    String 婚况 = getString(配置名称, "婚姻状况");
    String 属性 = getString(配置名称, "属性");
    String 状态 = getString(配置名称, "状态");
    String 神器 = getString(配置名称, "神器");
    String 级别 = getString(配置名称, "级别");
    String 天赋 = getString(配置名称, "天赋");
    String 阶段 = getString(配置名称, "阶段");
    long 等级 = 文转(getString(配置名称, "等级"));
    long 攻击 = 文转(getString(配置名称, "攻击"));
    long 防御 = 文转(getString(配置名称, "防御"));
    long 智力 = 文转(getString(配置名称, "智力"));
    long 当前精力 = 文转(getString(配置名称, "当前精力"));
    long 精力上限 = 文转(getString(配置名称, "精力上限"));
    long 当前生命 = 文转(getString(配置名称, "当前生命"));
    long 生命上限 = 文转(getString(配置名称, "生命上限"));
    long 当前经验 = 文转(getString(配置名称, "当前经验"));
    long 所需经验 = 文转(getString(配置名称, "下级所需经验"));
    
    // 该神器还没有设定相关属性时
    if (!itemAttrMap.containsKey(item)) {
       发送(qun, 玩家名(qun, uin) + " 无法装备神器！\n☹︎神器[" + item + "]暂未设定相关属性！", true);
    } else {
      long[] attrs = (long[]) itemAttrMap.get(item); // 获取神器对应的属性
      boolean hasExpectedCount = (attrs.length == 5);
      
      // 未设定属性 或 属性个数不足5个 时
      if (attrs == null || !hasExpectedCount) {
        发送(qun, 玩家名(qun, uin) + " 无法装备神器！\n☹︎神器〔" + item + "〕属性项目不全！", true);
      } else {      
         long 数量 = 1; // 扣除数量，固定为1
         long 道具 = 文转(getString(bagKey, item)); // 获取神器数量
         
         // 先从背包扣除一把该神器
         if (道具 - 数量 >= 1) {
             putString(bagKey, item, 转文(道具 - 数量));
         } else {
             String 备 = bag.replace("、" + item, "");
             String 北 = 备.replace(item + "、", "");
             if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                putString(bagKey, "道具列表", null);
                putString(bagKey, item, null);
             } else {
                putString(bagKey, "道具列表", 北);
                putString(bagKey, item, null);
             }
         }
         
         // 获取该神器的各项属性
         long attack = attrs[0];       // 攻击
         long defense = attrs[1];     // 防御
         long hp = attrs[2];          // 生命
         long intelligence = attrs[3]; // 智力
         long energy = attrs[4];      // 精力
         
         long 生命值 = (long) Math.floor(hp / 10); // 取整，方便后续计算
         long 战力提升 = (hp / 10) + attack + defense + (intelligence * 20);
         
         // 更新相关属性
         putString(配置名称, "攻击", 转文(攻击 + attack));
         putString(配置名称, "防御", 转文(防御 + defense));
         putString(配置名称, "智力", 转文(智力 + intelligence));
         putString(配置名称, "精力上限", 转文(精力上限 + energy));
         putString(配置名称, "生命上限", 转文(生命上限 + hp));
         putString(配置名称, "神器", item); // 记录装备的神器名称
         
         String 内容 = "您的【" + 宠物名 + "】戴着神器真是威风凌凌呢!\n● 佩戴神器：" + item + "\n● 生命提升：+" + 数值转(hp) + "\n● 攻击提升：+" + 数值转(attack) + "\n● 防御提升：+" + 数值转(defense) + "\n● 智力提升：+" + 数值转(intelligence) + "\n● 精力提升：+" + 数值转(energy) + "\n————————————\n▲ 战力提升：+" + 数值转(战力提升);
         发送(qun, 玩家名(qun, uin) + " " + 内容, true);
      }
    }
}

// 处理神器卸除时扣除的属性
public void 神器卸除(String qun, String uin, String item) {
    String 配置名 = qun + "/" + uin + "/";  
    String 配置名称 = 配置名 + "/宠物小窝/位置_0";
    String 宠物名 = getString(配置名称, "昵称");
    String bagKey = qun + "/" + uin + "/我的背包";
    String bag = getString(bagKey, "道具列表");
     
    // 出战宠物相关属性
    String 昵称 = getString(配置名称, "昵称");
    String 名字 = getString(配置名称, "宠物名字");
    String 性别 = getString(配置名称, "性别");
    String 婚况 = getString(配置名称, "婚姻状况");
    String 属性 = getString(配置名称, "属性");
    String 状态 = getString(配置名称, "状态");
    String 神器 = getString(配置名称, "神器");
    String 级别 = getString(配置名称, "级别");
    String 天赋 = getString(配置名称, "天赋");
    String 阶段 = getString(配置名称, "阶段");
    long 等级 = 文转(getString(配置名称, "等级"));
    long 攻击 = 文转(getString(配置名称, "攻击"));
    long 防御 = 文转(getString(配置名称, "防御"));
    long 智力 = 文转(getString(配置名称, "智力"));
    long 当前精力 = 文转(getString(配置名称, "当前精力"));
    long 精力上限 = 文转(getString(配置名称, "精力上限"));
    long 当前生命 = 文转(getString(配置名称, "当前生命"));
    long 生命上限 = 文转(getString(配置名称, "生命上限"));
    long 当前经验 = 文转(getString(配置名称, "当前经验"));
    long 所需经验 = 文转(getString(配置名称, "下级所需经验"));
    
    // 该神器还没有设定相关属性时
    if (!itemAttrMap.containsKey(item)) {
       发送(qun, 玩家名(qun, uin) + " 无法卸除神器！\n☹︎神器[" + item + "]暂未设定相关属性！", true);
    } else {
      long[] attrs = (long[]) itemAttrMap.get(item); // 获取神器对应的属性
      boolean hasExpectedCount = (attrs.length == 5);
      
      // 未设定属性 或 属性个数不足5个 时
      if (attrs == null || !hasExpectedCount) {
        发送(qun, 玩家名(qun, uin) + " 无法卸除神器！\n☹︎神器〔" + item + "〕属性项目不全！", true);
      } else {      
         long 数量 = 1; // 数量，固定为1
         
         // 判断背包中是否已存在该道具
         if (bag != null && bag.contains(item)) {
             // 存在则累加数量
             long count = 文转(getString(bagKey, item));
             putString(bagKey, item, 转文(count + 数量));
         } else {
             // 不存在则添加新道具
             String newBag = (bag == null ? "" : bag + "、") + item;
             putString(bagKey, "道具列表", newBag);
             putString(bagKey, item, 转文(数量));
         }
         
         // 获取该神器的各项属性
         long attack = attrs[0];       // 攻击
         long defense = attrs[1];     // 防御
         long hp = attrs[2];          // 生命
         long intelligence = attrs[3]; // 智力
         long energy = attrs[4];      // 精力
         
         long 生命值 = (long) Math.floor(hp / 10); // 取整，方便后续计算
         long 战力降低 = (hp / 10) + attack + defense + (intelligence * 20);
         
         // 更新相关属性
         putString(配置名称, "攻击", 转文(攻击 - attack));
         putString(配置名称, "防御", 转文(防御 - defense));
         putString(配置名称, "智力", 转文(智力 - intelligence));
         putString(配置名称, "精力上限", 转文(精力上限 - energy));
         putString(配置名称, "生命上限", 转文(生命上限 - hp));
         putString(配置名称, "神器", "无"); // 更新神器穿戴状况
         
         String 内容 = "卸下神器成功！没有神器的【" + 宠物名 + "】显得很落寞呢!\n• 成功卸下“" + 神器 + "”\n▼ 战力降低：-" + 数值转(战力降低);
         发送(qun, 玩家名(qun, uin) + " " + 内容, true);
      }
    }
}

/*
|序号|神器名称|神器数量|直接合成所需数量|
|---|---|---|---|
[1]聚气青锋：消耗龙珠×5可直接合成，聚气青锋×2可合成旋影游龙剑。
[2]旋影游龙剑，消耗龙珠×10可直接合成，旋影游龙剑×2可合成龙啸破云刃。
[3]龙啸破云刃，消耗龙珠×20可直接合成，龙啸破云刃×2可合成裂空星陨剑。
[4]裂空星陨剑，消耗龙珠×40可直接合成，裂空星陨剑×2可合成陨星龙脊剑。
[5]陨星龙脊剑，消耗龙珠×80可直接合成，陨星龙脊剑×2可合成九霄龙吟剑。
[6]九霄龙吟剑，消耗龙珠×160可直接合成，九霄龙吟剑×2可合成混沌盘龙刃。
[7]混沌盘龙刃，消耗龙珠×320可直接合成，混沌盘龙刃×2可合成万象龙纹戟。
[8]万象龙纹戟，消耗龙珠×640可直接合成，万象龙纹戟×2可合成太虚龙魄枪。
[9]太虚龙魄枪，消耗龙珠×1280可直接合成，太虚龙魄枪×2可合成焚世龙炎刀。
[10]焚世龙炎刀，消耗龙珠×2560可直接合成，焚世龙炎刀×2可合成镇狱龙渊斧。
[11]镇狱龙渊斧，消耗龙珠×5120可直接合成，镇狱龙渊斧×2可合成永恒龙晶锏。
[12]永恒龙晶锏，消耗龙珠×10240可直接合成，永恒龙晶锏×2可合成混沌祖龙杵。
[13]混沌祖龙杵，消耗龙珠×15000可直接合成，混沌祖龙杵×2可合成造化天龙钩。
[14]造化天龙钩，消耗龙珠×20000可直接合成，造化天龙钩×2可合成无上龙尊槊。
[15]无上龙尊槊，消耗龙珠×25000可直接合成，无上龙尊槊×2可合成混元终焉剑。
[16]混元终焉剑：龙珠×30000可直接合成。
*/
