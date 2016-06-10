var path = require('path');

export const DB = {
    SERVER: "localhost",
    USER: "Admin",
    PASSWORD: "salama",
    NAME: "dreamchat"
};

export const PORT = "1337";

export const BASE_PATH = path.resolve(__dirname, '');
export const BUILD_DIR = path.resolve(BASE_PATH + '/build');
export const SRC_DIR = path.resolve(BASE_PATH + '/src');

export const PUBLIC_DIR = path.resolve(BASE_PATH + '/public');

export const CLIENT_SRC_DIR = path.resolve(SRC_DIR + '/app');
export const SERVER_SRC_DIR = path.resolve(SRC_DIR + '/server');

export const CLIENT_BUILD_DIR = path.resolve(BUILD_DIR + '/client');
export const SERVER_BUILD_DIR = path.resolve(BUILD_DIR + '/server');
