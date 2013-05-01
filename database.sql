SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

CREATE TABLE IF NOT EXISTS `stats_blocks_broken` (
  `player_id` int(8) NOT NULL,
  `block_id` int(4) NOT NULL,
  `total` int(12) NOT NULL,
  UNIQUE KEY `unique` (`player_id`,`block_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS `stats_blocks_placed` (
  `player_id` int(8) NOT NULL,
  `block_id` int(4) NOT NULL,
  `total` int(12) NOT NULL,
  UNIQUE KEY `unique` (`player_id`,`block_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS `stats_mob_deaths` (
  `player_id` int(8) NOT NULL,
  `mob_name` varchar(16) collate utf8_unicode_ci NOT NULL,
  `total` int(12) NOT NULL,
  UNIQUE KEY `unique` (`player_id`,`mob_name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS `stats_mob_kills` (
  `player_id` int(8) NOT NULL,
  `mob_name` varchar(16) collate utf8_unicode_ci NOT NULL,
  `total` int(12) NOT NULL,
  UNIQUE KEY `unique` (`player_id`,`mob_name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS `stats_players` (
  `player_id` int(8) NOT NULL auto_increment,
  `player_name` varchar(16) collate utf8_unicode_ci NOT NULL,
  `player_first_join` int(10) NOT NULL default '0',
  `player_last_join` int(10) NOT NULL default '0',
  `player_time_online` int(10) NOT NULL default '0',
  `player_total_commands` int(8) NOT NULL default '0',
  `player_total_chat` int(8) NOT NULL default '0',
  PRIMARY KEY  (`player_id`),
  UNIQUE KEY `unique` (`player_name`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS `stats_player_kills` (
  `player_id` int(8) NOT NULL,
  `killed_player_id` int(8) NOT NULL,
  `total` int(12) NOT NULL,
  UNIQUE KEY `unique` (`player_id`,`killed_player_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS `stats_sessions` (
  `session_id` int(10) NOT NULL auto_increment,
  `player_id` int(8) unsigned NOT NULL,
  `session_time` int(10) NOT NULL,
  `session_status` smallint(1) NOT NULL DEFAULT '1',
  `session_blocks_placed` int(10) unsigned NOT NULL DEFAULT '0',
  `session_blocks_broken` int(10) unsigned NOT NULL DEFAULT '0',
  `session_time_spend` int(8) NOT NULL DEFAULT '0',
  `session_time_active` int(8) NOT NULL DEFAULT '0',
  `session_distance_travelled` int(8) unsigned NOT NULL DEFAULT '0',
  `session_mob_deaths` int(11) NOT NULL DEFAULT '0',
  `session_player_deaths` int(11) NOT NULL DEFAULT '0',
  `session_suicides` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`session_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
