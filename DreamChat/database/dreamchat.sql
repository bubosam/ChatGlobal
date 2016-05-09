-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Hostiteľ: 127.0.0.1
-- Čas generovania: Pi 06.Máj 2016, 10:06
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
(3, 'a019ed400268a575b4638727d8f2b4');

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `userID` int(5) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(20) DEFAULT NULL,
  `email` varchar(254) NOT NULL,
  `password` varchar(128) NOT NULL,
  PRIMARY KEY (`userID`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `email_2` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Sťahujem dáta pre tabuľku `users`
--

INSERT INTO `users` (`userID`, `nickname`, `email`, `password`) VALUES
(1, 'nemamradgulas', 'vymysleny@email.com', 'hesloheslo'),
(2, 'mickeymouse', 'mickey@mouse.sk', 'hesloheslo'),
(3, 'oskar', 'zase@prace.com', 'staralubovna'),
(4, 'donaldTrumpz', 'fake@donald.com', 'amerikausa'),
(5, 'dominikkolesar', 'dominik@kolesar.sk', '00000000');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
