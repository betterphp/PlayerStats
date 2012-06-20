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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=2 ;

CREATE TABLE IF NOT EXISTS `stats_player_kills` (
  `player_id` int(8) NOT NULL,
  `killed_player_id` int(8) NOT NULL,
  `total` int(12) NOT NULL,
  UNIQUE KEY `unique` (`player_id`,`killed_player_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;