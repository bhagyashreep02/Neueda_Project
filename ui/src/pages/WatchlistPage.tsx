import { useState, useEffect } from "react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog";
import { List, Plus, Trash2 } from "lucide-react";
import Navbar from "@/components/Navbar";
import { useNavigate } from "react-router-dom";
import { useToast } from "@/hooks/use-toast";

const WatchlistPage = () => {
  const navigate = useNavigate();
  const { toast } = useToast();
  const [user, setUser] = useState<{ email: string } | null>(null);
  const [isDialogOpen, setIsDialogOpen] = useState(false);

  const [ticker, setTicker] = useState("");
  const [price, setPrice] = useState("");
  const [prediction, setPrediction] = useState<Record<string, Prediction>>({});

  interface Prediction {
    predicted_next_open_price: number,
    last_10_open_prices: number[]
  };

  const [watchlist, setWatchlist] = useState<{ ticker: string; price: number }[]>([]);

  useEffect(() => {
    const email = localStorage.getItem("userEmail");
    if (email) setUser({ email });
    fetchWatchlist();
    //fetchPrediction();
  }, []);

  const fetchWatchlist = async () => {
    try {
      const res = await fetch("http://localhost:8080/watchlist");
      if (!res.ok) throw new Error("Failed to fetch watchlist");
      const data = await res.json();
      setWatchlist(data);
      fetchPredictions(data.map((stock: { ticker: string }) => stock.ticker)); // Fetch prediction after watchlist is loaded
    } catch (err) {
      toast({ title: "Error", description: String(err), variant: "destructive" });
    }
  };
  console.log(prediction)

  const fetchPredictions = async (symbols: string[]) => {
  try {
    const predictionsData: Record<string, Prediction> = {};

    for (const sym of symbols) {
      const res = await fetch(`http://localhost:8080/predict?symbol=${encodeURIComponent(sym)}`);
      if (!res.ok) throw new Error(`Failed to fetch prediction for ${sym}`);
      const data = await res.json();
      predictionsData[sym] = data;
    }

    setPrediction(predictionsData);
  } catch (err) {
    toast({ title: "Error", description: String(err), variant: "destructive" });
  }
};
  console.log(prediction);

  const handleSignOut = () => {
    localStorage.removeItem("authToken");
    localStorage.removeItem("userEmail");
    navigate("/");
  };

  const addToWatchlist = async () => {
    if (!ticker.trim()) {
      toast({ title: "Error", description: "Please enter both ticker and price", variant: "destructive" });
      return;
    }
    try {
      const res = await fetch(`http://localhost:8080/watchlist/add?ticker=${encodeURIComponent(ticker)}`, {
        method: "POST",
        
      });
      if (!res.ok) throw new Error("Failed to add stock");
      await fetchWatchlist();
      toast({ title: "Added to watchlist", description: `${ticker} added successfully.` });
      setIsDialogOpen(false);
      setTicker("");
      setPrice("");
    } catch (err) {
      toast({ title: "Error", description: String(err), variant: "destructive" });
    }
  };

  const removeFromWatchlist = async (ticker: string) => {
    try {
      const res = await fetch(`http://localhost:8080/watchlist/remove?ticker=${encodeURIComponent(ticker)}`, {
        method: "POST", // Changed from DELETE to POST
        
      });
      if (!res.ok) throw new Error("Failed to remove stock");
      await fetchWatchlist();
      toast({ title: "Removed from watchlist", description: `${ticker} removed successfully.` });
    } catch (err) {
      toast({ title: "Error", description: String(err), variant: "destructive" });
    }
  };

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
                <DialogDescription>Enter stock ticker</DialogDescription>
              </DialogHeader>
              <div className="space-y-4">
                <Input
                  placeholder="Enter ticker..."
                  value={ticker}
                  onChange={(e) => setTicker(e.target.value)}
                  className="bg-background/50 border-border/50"
                />
                <Button onClick={addToWatchlist} className="w-full">
                  Add
                </Button>
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
                  <TableHead>Price</TableHead>
                  <TableHead>Prediction</TableHead>
                  <TableHead>Actions</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {watchlist.map((stock) => (
                  <TableRow key={stock.ticker}>
                    <TableCell className="font-medium">{stock.ticker}</TableCell>
                    <TableCell>${stock.price}</TableCell>
                    <TableCell>
                      {prediction[stock.ticker] ? (
                        <div>
                          <p>Next Open: ${prediction[stock.ticker].predicted_next_open_price}</p>
                          <p>Last 10 Opens: {prediction[stock.ticker].last_10_open_prices.join(", ")}</p>
                        </div>
                      ) : (
                        <span className="text-muted-foreground">Loading...</span>
                      )}
                    </TableCell>
                    <TableCell>
                      <Button
                        variant="ghost"
                        size="sm"
                        onClick={() => removeFromWatchlist(stock.ticker)}
                        className="text-destructive hover:text-destructive"
                      >
                        <Trash2 className="h-4 w-4" />
                      </Button>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default WatchlistPage;
