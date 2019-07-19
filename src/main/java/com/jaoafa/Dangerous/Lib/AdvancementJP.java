package com.jaoafa.Dangerous.Lib;

public enum AdvancementJP {
	ADVENTURE_ADVENTURING_TIME("adventure/adventuring_time", "冒険の時間"),
	ADVENTURE_ARBALISTIC("adventure/arbalistic", "クロスボウの達人"),
	ADVENTURE_HERO_OF_THE_VILLAGE("adventure/hero_of_the_village", "村の英雄"),
	ADVENTURE_KILL_A_MOB("adventure/kill_a_mob", "モンスターハンター"),
	ADVENTURE_KILL_ALL_MOBS("adventure/kill_all_mobs", "モンスター狩りの達人"),
	ADVENTURE_OL_BETSY("adventure/ol_betsy", "おてんば"),
	ADVENTURE_ROOT("adventure/root", "冒険"),
	ADVENTURE_SHOOT_ARROW("adventure/shoot_arrow", "狙いを定めて"),
	ADVENTURE_SLEEP_IN_BED("adventure/sleep_in_bed", "良い夢見てね"),
	ADVENTURE_SNIPER_DUEL("adventure/sniper_duel", "スナイパー対決"),
	ADVENTURE_SUMMON_IRON_GOLEM("adventure/summon_iron_golem", "お手伝いさん"),
	ADVENTURE_THROW_TRIDENT("adventure/throw_trident", "もったいぶった一言"),
	ADVENTURE_TOTEM_OF_UNDYING("adventure/totem_of_undying", "死を超えて"),
	ADVENTURE_TRADE("adventure/trade", "良い取引だ！"),
	ADVENTURE_TWO_BIRDS_ONE_ARROW("adventure/two_birds_one_arrow", "一石二鳥"),
	ADVENTURE_VERY_VERY_FRIGHTENING("adventure/very_very_frightening", "とてもとても恐ろしい"),
	ADVENTURE_VOLUNTARY_EXILE("adventure/voluntary_exile", "自主的な亡命"),
	ADVENTURE_WHOS_THE_PILLAGER_NOW("adventure/whos_the_pillager_now", "どっちが略奪者？"),
	END_DRAGON_BREATH("end/dragon_breath", "口臭に気をつけよう"),
	END_DRAGON_EGG("end/dragon_egg", "ザ・ネクストジェネレーション"),
	END_ELYTRA("end/elytra", "空はどこまでも高く"),
	END_ENTER_END_GATEWAY("end/enter_end_gateway", "遠方への逃走"),
	END_FIND_END_CITY("end/find_end_city", "ゲームの果ての都市"),
	END_KILL_DRAGON("end/kill_dragon", "エンドの解放"),
	END_LEVITATE("end/levitate", "ここからの素晴らしい眺め"),
	END_RESPAWN_DRAGON("end/respawn_dragon", "おしまい…再び…"),
	END_ROOT("end/root", "ジ・エンド"),
	HUSBANDRY_BALANCED_DIET("husbandry/balanced_diet", "バランスの取れた食事"),
	HUSBANDRY_BREAK_DIAMOND_HOE("husbandry/break_diamond_hoe", "真面目な献身"),
	HUSBANDRY_BREED_ALL_ANIMALS("husbandry/breed_all_animals", "二匹ずつ"),
	HUSBANDRY_BREED_AN_ANIMAL("husbandry/breed_an_animal", "コウノトリの贈り物"),
	HUSBANDRY_COMPLETE_CATALOGUE("husbandry/complete_catalogue", "猫大全集"),
	HUSBANDRY_FISHY_BUSINESS("husbandry/fishy_business", "生臭い仕事"),
	HUSBANDRY_PLANT_SEED("husbandry/plant_seed", "種だらけの場所"),
	HUSBANDRY_ROOT("husbandry/root", "農業"),
	HUSBANDRY_TACTICAL_FISHING("husbandry/tactical_fishing", "戦術的漁業"),
	HUSBANDRY_TAME_AN_ANIMAL("husbandry/tame_an_animal", "永遠の親友となるだろう"),
	NETHER_ALL_EFFECTS("nether/all_effects", "どうやってここまで？"),
	NETHER_ALL_POTIONS("nether/all_potions", "猛烈なカクテル"),
	NETHER_BREW_POTION("nether/brew_potion", "町のお薬屋さん"),
	NETHER_CREATE_BEACON("nether/create_beacon", "生活のビーコン"),
	NETHER_CREATE_FULL_BEACON("nether/create_full_beacon", "ビーコネーター"),
	NETHER_FAST_TRAVEL("nether/fast_travel", "亜空間バブル"),
	NETHER_FIND_FORTRESS("nether/find_fortress", "恐ろしい要塞"),
	NETHER_GET_WITHER_SKULL("nether/get_wither_skull", "不気味で怖いスケルトン"),
	NETHER_OBTAIN_BLAZE_ROD("nether/obtain_blaze_rod", "炎の中へ"),
	NETHER_RETURN_TO_SENDER("nether/return_to_sender", "差出人に返送"),
	NETHER_ROOT("nether/root", "ネザー"),
	NETHER_SUMMON_WITHER("nether/summon_wither", "荒が丘"),
	NETHER_UNEASY_ALLIANCE("nether/uneasy_alliance", "不安な同盟"),
	STORY_CURE_ZOMBIE_VILLAGER("story/cure_zombie_villager", "ゾンビドクター"),
	STORY_DEFLECT_ARROW("story/deflect_arrow", "今日はやめておきます"),
	STORY_ENCHANT_ITEM("story/enchant_item", "エンチャントの使い手"),
	STORY_ENTER_THE_END("story/enter_the_end", "おしまい？"),
	STORY_ENTER_THE_NETHER("story/enter_the_nether", "さらなる深みへ"),
	STORY_FOLLOW_ENDER_EYE("story/follow_ender_eye", "アイ・スパイ"),
	STORY_FORM_OBSIDIAN("story/form_obsidian", "アイス・バケツ・チャレンジ"),
	STORY_IRON_TOOLS("story/iron_tools", "鉄のツルハシで決まり"),
	STORY_LAVA_BUCKET("story/lava_bucket", "ホットスタッフ"),
	STORY_MINE_DIAMOND("story/mine_diamond", "ダイヤモンド！"),
	STORY_MINE_STONE("story/mine_stone", "石器時代"),
	STORY_OBTAIN_ARMOR("story/obtain_armor", "装備せよ"),
	STORY_ROOT("story/root", "Minecraft"),
	STORY_SHINY_GEAR("story/shiny_gear", "ダイヤモンドで私を覆って"),
	STORY_SMELT_IRON("story/smelt_iron", "金属を手に入れる"),
	STORY_UPGRADE_TOOLS("story/upgrade_tools", "アップグレード"),
	UNKNOWN("unknown", "unknown");

	String key;
	String name;
	AdvancementJP(String key, String name){
		this.key = key;
		this.name = name;
	}
	public String getKey(){
		return key;
	}
	public String getName(){
		return name;
	}
	public static AdvancementJP getAdvancementJPFromKey(String key){
		for(AdvancementJP advancement : AdvancementJP.values()){
			if(advancement.key.equals(key)){
				return advancement;
			}
		}
		return UNKNOWN;
	}
}
