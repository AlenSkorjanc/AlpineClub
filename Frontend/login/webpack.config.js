const HtmlWebPackPlugin = require("html-webpack-plugin");
const ModuleFederationPlugin = require("webpack/lib/container/ModuleFederationPlugin");
const Dotenv = require('dotenv-webpack');
require('dotenv').config();

const deps = require("./package.json").dependencies;

var publicPath = "http://localhost:3001/";
if(process.env.RELEASE_STAGE === "production") {
  publicPath = "https://login-alenskorjanc-dev.apps.sandbox-m2.ll9k.p1.openshiftapps.com/";
}

console.log(`Release stage: ${process.env.RELEASE_STAGE}`);

module.exports = (_, argv) => ({
  output: {
    publicPath: publicPath,
  },

  resolve: {
    extensions: [".tsx", ".ts", ".jsx", ".js", ".json"],
  },

  devServer: {
    port: 3001,
    historyApiFallback: true,
    headers: {
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Methods": "GET, POST, PUT, DELETE, PATCH, OPTIONS",
      "Access-Control-Allow-Headers": "*"
    },
    host: '0.0.0.0',
    allowedHosts: [
      'localhost',
      'login',
      'login-alenskorjanc-dev.apps.sandbox-m2.ll9k.p1.openshiftapps.com'
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
      name: "login",
      filename: "remoteEntry.js",
      remotes: {},
      exposes: {
        "./Login": "./src/Login.jsx"
      },
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
    new Dotenv()
  ],
});
