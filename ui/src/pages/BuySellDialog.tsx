import { useState } from "react";
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogDescription, DialogTrigger } from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Plus } from "lucide-react";

const BuySellDialog = ({ isOpen, setIsOpen }) => {
  const [step, setStep] = useState(1);
  const [action, setAction] = useState(""); // "BUY" or "SELL"
  const [ticker, setTicker] = useState("");
  const [quantity, setQuantity] = useState("");
  const [price, setPrice] = useState("");

  const handleActionSelect = (selectedAction) => {
    setAction(selectedAction);
    setStep(2);
  };

  const handleTransact = async () => {
    try {
      const res = await fetch("http://localhost:8080/transact", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          ticker,
          quantity: parseInt(quantity, 10),
          price: parseFloat(price),
          action
        }),
      });

      if (!res.ok) throw new Error("Transaction failed");

      alert(`${action} order for ${ticker} submitted successfully`);
      setIsOpen(false);
      setStep(1);
      setAction("");
      setTicker("");
      setQuantity("");
      setPrice("");
    } catch (error) {
      console.error(error);
      alert("Failed to submit transaction");
    }
  };

  return (
    <Dialog open={isOpen} onOpenChange={setIsOpen}>
      <DialogTrigger asChild>
        <Button variant="hero">
          <Plus className="h-4 w-4 mr-2" />
          Buy/Sell Stock
        </Button>
      </DialogTrigger>

      <DialogContent className="sm:max-w-[425px] bg-[var(--gradient-card)] border-border/50">
        <DialogHeader>
          <DialogTitle>{step === 1 ? "Choose Action" : `Enter ${action} Details`}</DialogTitle>
          <DialogDescription>
            {step === 1
              ? "Would you like to buy or sell?"
              : `Provide details for your ${action} order`}
          </DialogDescription>
        </DialogHeader>

        {step === 1 && (
          <div className="flex gap-4">
            <Button onClick={() => handleActionSelect("BUY")} className="flex-1 bg-gain">Buy</Button>
            <Button onClick={() => handleActionSelect("SELL")} className="flex-1 bg-loss">Sell</Button>
          </div>
        )}

        {step === 2 && (
          <div className="space-y-4">
            <Input
              placeholder="Enter ticker..."
              value={ticker}
              onChange={(e) => setTicker(e.target.value)}
              className="bg-background/50 border-border/50"
            />
            <Input
              placeholder="Enter quantity..."
              type="number"
              value={quantity}
              onChange={(e) => setQuantity(e.target.value)}
              className="bg-background/50 border-border/50"
            />
            <Input
              placeholder="Enter price..."
              type="number"
              step="0.01"
              value={price}
              onChange={(e) => setPrice(e.target.value)}
              className="bg-background/50 border-border/50"
            />
            <div className="flex justify-between">
              <Button variant="secondary" onClick={() => setStep(1)}>Back</Button>
              <Button onClick={async () => { await handleTransact(); location.reload(); }} className="bg-gain">
                Confirm {action}
              </Button>
            </div>
          </div>
        )}
      </DialogContent>
    </Dialog>
  );
};

export default BuySellDialog;
