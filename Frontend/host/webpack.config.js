const HtmlWebPackPlugin = require("html-webpack-plugin");
const ModuleFederationPlugin = require("webpack/lib/container/ModuleFederationPlugin");
const Dotenv = require('dotenv-webpack');
require('dotenv').config();

const deps = require("./package.json").dependencies;

console.log(`Release stage: ${process.env.RELEASE_STAGE}`);
console.log(`Host: ${process.env.PUBLIC_PATH.replace('https://', '').replace('/', '')}`);
module.exports = (_, args) => {
  return ({
    output: {
      publicPath: process.env.PUBLIC_PATH,
    },

    resolve: {
      extensions: [".tsx", ".ts", ".jsx", ".js", ".json"],
    },

    devServer: {
      port: 3000,
      historyApiFallback: true,
      host: '0.0.0.0',
      allowedHosts: [
        'localhost',
        'alpclub',
        process.env.PUBLIC_PATH.replace('https://', '').replace('/', '')
      ]
    },

    module: {
      rules: [
        {
          test: /\.m?js/,
          type: "javascript/auto",
          resolve: {
            fullySpecified: false,
          },
        },
        {
          test: /\.(css|s[ac]ss)$/i,
          use: ["style-loader", "css-loader", "postcss-loader"],
        },
        {
          test: /\.(ts|tsx|js|jsx)$/,
          exclude: /node_modules/,
          use: {
            loader: "babel-loader",
          },
        },
      ],
    },

    plugins: [
      new ModuleFederationPlugin({
        name: "host",
        filename: "remoteEntry.js",
        remotes: {
          login: `login@${process.env.LOGIN_REMOTE_ENTRY}/remoteEntry.js`,
          articles: `articles@${process.env.ARTICLES_REMOTE_ENTRY}/remoteEntry.js`,
          events_mf: `events_mf@${process.env.EVENTS_REMOTE_ENTRY}/remoteEntry.js`
        },
        exposes: {},
        shared: {
          ...deps,
          "solid-js": {
            singleton: true,
            requiredVersion: deps["solid-js"],
          },
        },
      }),
      new HtmlWebPackPlugin({
        template: "./src/index.html",
      }),
      new Dotenv({
        systemvars: true
      })
    ],
  })
};
