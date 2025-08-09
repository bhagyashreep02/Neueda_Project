import { useState, useEffect } from "react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog";
import { Plus, Trash2, Search, TrendingUp, TrendingDown } from "lucide-react";
import Navbar from "@/components/Navbar";
import { useNavigate } from "react-router-dom";
import { useToast } from "@/hooks/use-toast";

const WatchlistPage = () => {
  const navigate = useNavigate();
  const { toast } = useToast();
  const [user, setUser] = useState<{ email: string } | null>(null);
  const [searchSymbol, setSearchSymbol] = useState("");
  const [isDialogOpen, setIsDialogOpen] = useState(false);

  const [watchlist, setWatchlist] = useState([
    { symbol: "AAPL", name: "Apple Inc.", price: 185.23, change: 3.45, changePercent: 1.9, volume: "52.3M" },
    { symbol: "GOOGL", name: "Alphabet Inc.", price: 2725.15, change: -12.30, changePercent: -0.45, volume: "28.7M" },
    { symbol: "MSFT", name: "Microsoft Corporation", price: 328.67, change: 7.89, changePercent: 2.46, volume: "41.2M" },
    { symbol: "TSLA", name: "Tesla Inc.", price: 238.45, change: -8.75, changePercent: -3.54, volume: "89.1M" },
    { symbol: "AMZN", name: "Amazon.com Inc.", price: 3245.67, change: 15.23, changePercent: 0.47, volume: "35.6M" },
  ]);

  // Mock search results
  const searchResults = [
    { symbol: "NVDA", name: "NVIDIA Corporation", price: 742.33, change: 45.67, changePercent: 6.57, volume: "67.8M" },
    { symbol: "META", name: "Meta Platforms Inc.", price: 412.85, change: -5.67, changePercent: -1.35, volume: "23.4M" },
    { symbol: "NFLX", name: "Netflix Inc.", price: 456.78, change: 12.34, changePercent: 2.78, volume: "18.9M" },
  ];

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

  const addToWatchlist = (stock: typeof searchResults[0]) => {
    if (!watchlist.find(item => item.symbol === stock.symbol)) {
      setWatchlist([...watchlist, stock]);
      toast({
        title: "Added to watchlist",
        description: `${stock.symbol} has been added to your watchlist.`,
      });
    } else {
      toast({
        title: "Already in watchlist",
        description: `${stock.symbol} is already in your watchlist.`,
        variant: "destructive",
      });
    }
    setIsDialogOpen(false);
    setSearchSymbol("");
  };

  const removeFromWatchlist = (symbol: string) => {
    setWatchlist(watchlist.filter(item => item.symbol !== symbol));
    toast({
      title: "Removed from watchlist",
      description: `${symbol} has been removed from your watchlist.`,
    });
  };

  const filteredSearchResults = searchResults.filter(stock =>
    stock.symbol.toLowerCase().includes(searchSymbol.toLowerCase()) ||
    stock.name.toLowerCase().includes(searchSymbol.toLowerCase())
  );

  return (
    <div className="min-h-screen bg-background">
      <Navbar user={user} onSignOut={handleSignOut} />
      
      <div className="container mx-auto px-4 py-8">
        <div className="mb-8 flex items-center justify-between">
          <div>
            <h1 className="text-3xl font-bold mb-2">Watchlist</h1>
            <p className="text-muted-foreground">Monitor your favorite stocks</p>
          </div>
          
          <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
            <DialogTrigger asChild>
              <Button variant="hero">
                <Plus className="h-4 w-4 mr-2" />
                Add Stock
              </Button>
            </DialogTrigger>
            <DialogContent className="sm:max-w-[425px] bg-[var(--gradient-card)] border-border/50">
              <DialogHeader>
                <DialogTitle>Add Stock to Watchlist</DialogTitle>
                <DialogDescription>
                  Search for stocks to add to your watchlist
                </DialogDescription>
              </DialogHeader>
              <div className="space-y-4">
                <div className="relative">
                  <Search className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
                  <Input
                    placeholder="Search by symbol or company name..."
                    value={searchSymbol}
                    onChange={(e) => setSearchSymbol(e.target.value)}
                    className="pl-10 bg-background/50 border-border/50"
                  />
                </div>
                
                <div className="max-h-60 overflow-y-auto space-y-2">
                  {filteredSearchResults.map((stock) => (
                    <div 
                      key={stock.symbol}
                      className="flex items-center justify-between p-3 rounded-lg border border-border/50 hover:bg-accent/50 cursor-pointer"
                      onClick={() => addToWatchlist(stock)}
                    >
                      <div>
                        <div className="font-medium">{stock.symbol}</div>
                        <div className="text-sm text-muted-foreground">{stock.name}</div>
                      </div>
                      <div className="text-right">
                        <div className="font-medium">${stock.price.toFixed(2)}</div>
                        <div className={`text-sm flex items-center gap-1 ${stock.change > 0 ? 'text-gain' : 'text-loss'}`}>
                          {stock.change > 0 ? (
                            <TrendingUp className="h-3 w-3" />
                          ) : (
                            <TrendingDown className="h-3 w-3" />
                          )}
                          {stock.changePercent}%
                        </div>
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            </DialogContent>
          </Dialog>
        </div>

        <Card className="bg-[var(--gradient-card)] border-border/50">
          <CardHeader>
            <CardTitle>Your Watchlist</CardTitle>
            <CardDescription>Tracking {watchlist.length} stocks</CardDescription>
          </CardHeader>
          <CardContent>
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Symbol</TableHead>
                  <TableHead>Company</TableHead>
                  <TableHead>Price</TableHead>
                  <TableHead>Change</TableHead>
                  <TableHead>Change %</TableHead>
                  <TableHead>Volume</TableHead>
                  <TableHead>Actions</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {watchlist.map((stock) => {
                  const isPositive = stock.change > 0;
                  
                  return (
                    <TableRow key={stock.symbol}>
                      <TableCell className="font-medium">{stock.symbol}</TableCell>
                      <TableCell>{stock.name}</TableCell>
                      <TableCell>${stock.price.toFixed(2)}</TableCell>
                      <TableCell className={isPositive ? "text-gain" : "text-loss"}>
                        {isPositive ? "+" : ""}${stock.change.toFixed(2)}
                      </TableCell>
                      <TableCell>
                        <Badge 
                          variant={isPositive ? "default" : "destructive"} 
                          className={isPositive ? "bg-gain" : ""}
                        >
                          {isPositive ? "+" : ""}{stock.changePercent}%
                        </Badge>
                      </TableCell>
                      <TableCell>{stock.volume}</TableCell>
                      <TableCell>
                        <Button
                          variant="ghost"
                          size="sm"
                          onClick={() => removeFromWatchlist(stock.symbol)}
                          className="text-destructive hover:text-destructive"
                        >
                          <Trash2 className="h-4 w-4" />
                        </Button>
                      </TableCell>
                    </TableRow>
                  );
                })}
              </TableBody>
            </Table>
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default WatchlistPage;