import { useState, useEffect } from "react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { TrendingUp, TrendingDown, DollarSign, PieChart, Plus } from "lucide-react";
import Navbar from "@/components/Navbar";
import { useNavigate } from "react-router-dom";
import { Portal } from "vaul";

const DashboardPage = () => {
  const navigate = useNavigate();
  const [user, setUser] = useState<{ email: string } | null>(null);
  
  
  interface Holding {
  ticker: string;
  quantity: number;
}
  
  interface Portfolio {
  currentHoldings: Record<string, Holding>;
  totalGain: number;
  currentMarketValue: number;
  portfolioValue: number;
  portfolioPerformance: number;
}

const [portfolioData, setPortfolioData] = useState<Portfolio | null>(null);
  
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch('http://localhost:8080/');
        const data = await response.json();
        setPortfolioData(data);
      } catch (error) {
        console.error('Error fetching portfolio data:', error);
      }
    };
    fetchData();
  }, []);
  console.log(portfolioData);

  const [allOrders, setAllOrders] = useState([]);
  
  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const response = await fetch('http://localhost:8080/orders');
        const data = await response.json();
        setAllOrders(data.allOrders || []);
      } catch (error) {
        console.error('Error fetching orders:', error);
      }
    };
    fetchOrders();
  }, []);
  
  console.log(allOrders);

  

  // Mock portfolio data
  const [portfolio] = useState({
    totalValue: 127523.45,
    dayChange: 2847.32,
    dayChangePercent: 2.29,
    holdings: [
      { symbol: "AAPL", shares: 50, avgPrice: 178.45, currentPrice: 185.23, change: 3.8 },
      { symbol: "GOOGL", shares: 25, avgPrice: 2650.30, currentPrice: 2725.15, change: 2.8 },
      { symbol: "MSFT", shares: 40, avgPrice: 315.20, currentPrice: 328.67, change: 4.3 },
      { symbol: "TSLA", shares: 30, avgPrice: 245.80, currentPrice: 238.45, change: -3.0 },
      { symbol: "NVDA", shares: 15, avgPrice: 685.40, currentPrice: 742.33, change: 8.3 },
    ]
  });

  const [recentTransactions] = useState([
    { id: 1, symbol: "AAPL", type: "BUY", shares: 10, price: 185.23, date: "2024-01-15" },
    { id: 2, symbol: "MSFT", type: "SELL", shares: 5, price: 328.67, date: "2024-01-14" },
    { id: 3, symbol: "NVDA", type: "BUY", shares: 5, price: 742.33, date: "2024-01-13" },
    { id: 4, symbol: "GOOGL", type: "BUY", shares: 3, price: 2725.15, date: "2024-01-12" },
  ]);

  useEffect(() => {
    const email = localStorage.getItem('userEmail');
    if (email) {
      setUser({ email });
    }
  }, []);

  const handleSignOut = () => {
    localStorage.removeItem('authToken');
    localStorage.removeItem('userEmail');
    navigate('/');
  };

  const calculateHoldingValue = (shares: number, price: number) => shares * price;
  const calculatePnL = (shares: number, avgPrice: number, currentPrice: number) => 
    shares * (currentPrice - avgPrice);

  return (
    <div className="min-h-screen bg-background">
      <Navbar user={user} onSignOut={handleSignOut} />
      
      <div className="container mx-auto px-4 py-8">
        <div className="mb-8">
          <h1 className="text-3xl font-bold mb-2">Portfolio Dashboard</h1>
          <p className="text-muted-foreground">Track your investments and performance</p>
        </div>

        {/* Portfolio Summary Cards */}
        <div className="grid md:grid-cols-3 gap-6 mb-8">
          <Card className="bg-[var(--gradient-card)] border-border/50">
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">Total Portfolio Value</CardTitle>
              <DollarSign className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">${portfolioData?.portfolioValue}</div>
              <div className="flex items-center gap-1 text-sm">
                {portfolio.dayChange > 0 ? (
                  <TrendingUp className="h-4 w-4 text-gain" />
                ) : (
                  <TrendingDown className="h-4 w-4 text-loss" />
                )}
                <span className={portfolioData?.totalGain > 0 ? "text-gain" : "text-loss"}>
                  ${Math.abs(portfolioData?.totalGain).toLocaleString()} ({portfolio.dayChangePercent}%)
                </span>
              </div>
            </CardContent>
          </Card>

          <Card className="bg-[var(--gradient-card)] border-border/50">
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">Holdings</CardTitle>
              <PieChart className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">{portfolioData?.currentHoldings
                ? Object.keys(portfolioData.currentHoldings).length
                : 'Loading...'}</div>
              <p className="text-xs text-muted-foreground">Active positions</p>
            </CardContent>
          </Card>

          <Card className="bg-[var(--gradient-card)] border-border/50">
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">Current Market Value</CardTitle>
              <TrendingUp className="h-4 w-4 text-gain" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">{portfolioData?.currentMarketValue}</div>
            </CardContent>
          </Card>
        </div>

        <Tabs defaultValue="holdings" className="space-y-6">
          <TabsList className="grid w-full grid-cols-2 bg-muted/50">
            <TabsTrigger value="holdings">Holdings</TabsTrigger>
            <TabsTrigger value="transactions">Recent Transactions</TabsTrigger>
          </TabsList>

          <TabsContent value="holdings">
            <Card className="bg-[var(--gradient-card)] border-border/50">
              <CardHeader className="flex flex-row items-center justify-between">
                <div>
                  <CardTitle>Current Holdings</CardTitle>
                  <CardDescription>Your active stock positions</CardDescription>
                </div>
                <Button variant="outline" size="sm">
                  <Plus className="h-4 w-4 mr-2" />
                  Add Position
                </Button>
              </CardHeader>
              <CardContent>
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>Symbol</TableHead>
                      <TableHead>Shares</TableHead>
                      <TableHead>Avg Price</TableHead>
                      <TableHead>Current Price</TableHead>
                      <TableHead>Value</TableHead>
                      <TableHead>P&L</TableHead>
                      <TableHead>Change %</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    
                    {allOrders.map((order) => {

                      const holding = {
                        symbol: order.stockTicker,
                        shares: order.volume,
                        priceOfBuying: order.priceOfBuying,
                      };
                    
                      const avgPrice = holding.priceOfBuying/holding.shares;
                      const value = calculateHoldingValue(holding.shares, holding.priceOfBuying);
                      const pnl = calculatePnL(holding.shares, avgPrice, holding.priceOfBuying);
                      const isPositive = pnl >= 0;
                      const change = ((holding.priceOfBuying - avgPrice) / avgPrice * 100).toFixed(2);
                      
                      return (
                        <TableRow key={holding.symbol}>
                          <TableCell className="font-medium">{holding.symbol}</TableCell>
                          <TableCell>{holding.shares}</TableCell>
                          <TableCell>${avgPrice.toFixed(2)}</TableCell>
                          <TableCell>${holding.priceOfBuying.toFixed(2)}</TableCell>
                          <TableCell>${value.toLocaleString()}</TableCell>
                          <TableCell className={pnl > 0 ? "text-gain" : "text-loss"}>
                            ${pnl.toFixed(2)}
                          </TableCell>
                          <TableCell>
                            <Badge variant={isPositive ? "default" : "destructive"} className={isPositive ? "bg-gain" : ""}>
                              {isPositive ? "+" : ""}{change}%
                            </Badge>
                          </TableCell>
                        </TableRow>
                      );
                    })}
                  </TableBody>
                </Table>
              </CardContent>
            </Card>
          </TabsContent>

          <TabsContent value="transactions">
            <Card className="bg-[var(--gradient-card)] border-border/50">
              <CardHeader>
                <CardTitle>Recent Transactions</CardTitle>
                <CardDescription>Your latest trading activity</CardDescription>
              </CardHeader>
              <CardContent>
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>Symbol</TableHead>
                      <TableHead>Type</TableHead>
                      <TableHead>Shares</TableHead>
                      <TableHead>Price</TableHead>
                      <TableHead>Total</TableHead>
                      <TableHead>Date</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {allOrders.map((order) => {

                      const holding = {
                        symbol: order.stockTicker,
                        shares: order.volume,
                        priceOfBuying: order.priceOfBuying,
                        buySell: order.buySell,
                        time: order.timestamp,
                      };
                    
                      const avgPrice = holding.priceOfBuying/holding.shares;
                      const value = calculateHoldingValue(holding.shares, holding.priceOfBuying);
                      const pnl = calculatePnL(holding.shares, avgPrice, holding.priceOfBuying);
                      const isPositive = pnl >= 0;
                      const change = ((holding.priceOfBuying - avgPrice) / avgPrice * 100).toFixed(2);
                      
                    // {recentTransactions.map((transaction) => (
                    return (
                      <TableRow key={holding.symbol}>
                        <TableCell className="font-medium">{holding.symbol}</TableCell>
                        <TableCell>
                          <Badge variant={holding.buySell === 1 ? "default" : "secondary"}>
                            {holding.buySell === 1 ? "BUY" : "SELL"}
                          </Badge>
                        </TableCell>
                        <TableCell>{holding.shares}</TableCell>
                        <TableCell>${holding.priceOfBuying.toFixed(2)}</TableCell>
                        <TableCell>${(holding.shares * holding.priceOfBuying).toLocaleString()}</TableCell>
                        <TableCell>{new Date(holding.time).toISOString().slice(0, 10)}</TableCell>
                      </TableRow>
                    );})}
                  </TableBody>
                </Table>
              </CardContent>
            </Card>
          </TabsContent>
        </Tabs>
      </div>
    </div>
  );
};

export default DashboardPage;