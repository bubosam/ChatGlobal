-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Hostiteľ: 127.0.0.1
-- Čas generovania: Št 12.Máj 2016, 11:33
-- Verzia serveru: 5.7.9
-- Verzia PHP: 5.6.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Databáza: `dreamchat`
--

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `friendships`
--

DROP TABLE IF EXISTS `friendships`;
CREATE TABLE IF NOT EXISTS `friendships` (
  `friendshipID` int(11) NOT NULL AUTO_INCREMENT,
  `user1` int(11) NOT NULL,
  `user2` int(11) NOT NULL,
  PRIMARY KEY (`friendshipID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `friend_requests`
--

DROP TABLE IF EXISTS `friend_requests`;
CREATE TABLE IF NOT EXISTS `friend_requests` (
  `requestID` int(11) NOT NULL AUTO_INCREMENT,
  `sender` int(11) NOT NULL,
  `receiver` int(11) NOT NULL,
  PRIMARY KEY (`requestID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `tokens`
--

DROP TABLE IF EXISTS `tokens`;
CREATE TABLE IF NOT EXISTS `tokens` (
  `userID` int(5) NOT NULL,
  `token` char(32) NOT NULL,
  UNIQUE KEY `token` (`token`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Sťahujem dáta pre tabuľku `tokens`
--

INSERT INTO `tokens` (`userID`, `token`) VALUES
(3, 'a019ed400268a575b4638727d8f2b4'),
(2, '86b36a19041b9b7401523fb05371c4');

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `userID` int(5) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(20) DEFAULT NULL,
  `email` varchar(254) NOT NULL,
  `password` varchar(16) NOT NULL,
  `phone` char(15) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `surname` varchar(20) DEFAULT NULL,
  `avatar` char(1) DEFAULT NULL,
  PRIMARY KEY (`userID`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `email_2` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

--
-- Sťahujem dáta pre tabuľku `users`
--

INSERT INTO `users` (`userID`, `nickname`, `email`, `password`, `phone`, `name`, `surname`, `avatar`) VALUES
(1, 'nemamradgulas', 'vymysleny@email.com', 'hesloheslo', NULL, NULL, NULL, NULL),
(2, 'mickeymouse', 'mickey@mouse.sk', 'hesloheslo', NULL, NULL, NULL, NULL),
(3, 'oskar', 'zase@prace.com', 'staralubovna', NULL, NULL, NULL, NULL),
(4, 'donaldTrumpz', 'fake@donald.com', 'amerikausa', NULL, NULL, NULL, NULL),
(5, 'dominikkolesar', 'dominik@kolesar.sk', '00000000', NULL, NULL, NULL, NULL),
(6, 'breanko1', 'branko@centrum.sk', 'mrkvicka', NULL, NULL, NULL, NULL),
(7, 'marekGamer111', 'karol@bandaska.hu', 'marekjelama123', NULL, NULL, NULL, NULL),
(8, 'svetovymier', 'jan@pavol.eu', 'nechzijeboh', NULL, NULL, NULL, NULL),
(9, 'lubko', 'lubomir@biely.eu', 'YOLOLOLO', NULL, NULL, NULL, NULL),
(10, 'netreba', 'ten@koho.eu', 'menovat123', NULL, NULL, NULL, NULL);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
