# Java Options Derivatives Algo Trading System

This is an algorithmic trading system written in Java for trading **options derivatives** across multiple brokers, with functionality for **live trading**, **position management**, **backtesting** and **fronttesting**. 

## 🔧 Features

- ⚙️ **Broker API Integration**:
  - **Zerodha** – Order placement, position management, and live positions
  - **Kotak Neo** – similar as Zerodha Kite, implemented for lower brokerage using Student Plan
  - **Groww** – Addition market data

- 📊 **Market Data & Indicators**:
  - **TradingView** – Used for real-time data and indicators for strategy execution
  - **Groww** – Secondary source for live data

- 📈 **Strategy Implementation**:
  - Strategy logic based on TradingView indicators and Java-based execution modules.

- 🧪 **Backtesting**:
  - Located in the `/backtesting` folder
  - Has historical OHLC and CCI indicator data
  - Helps evaluate strategy performance

- 🖼️ **Fronttesting Results**:
  - Screenshots of live test runs and results in `/screenshots` folder

## ⚠️ Note on Credentials

Currently, **credentials are hardcoded** directly into the respective API integration files. You will need to manually input your API keys, tokens, and other credentials.  
**Credential management and security will be improved in future updates.**

<br/>
Thanks,

Anurag.
