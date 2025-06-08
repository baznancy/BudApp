// src/components/TransactionFilter.tsx

import React, { useState } from 'react';

interface FilterProps {
    onFilter: (filters: { startDate: string; endDate: string; type: string }) => void;
}

const TransactionFilter: React.FC<FilterProps> = ({ onFilter }) => {
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [type, setType] = useState('');

    const handleFilter = () => {
        onFilter({ startDate, endDate, type });
    };

    return (
        <div>
            <input
                type="datetime-local"
                value={startDate}
                onChange={(e) => setStartDate(e.target.value)}
                placeholder="Start Date"
            />
            <input
                type="datetime-local"
                value={endDate}
                onChange={(e) => setEndDate(e.target.value)}
                placeholder="End Date"
            />
            <select value={type} onChange={(e) => setType(e.target.value)}>
                <option value="">All Types</option>
                <option value="INCOME">Income</option>
                <option value="EXPENSE">Expense</option>
            </select>
            <button onClick={handleFilter}>Filter</button>
        </div>
    );
};

export default TransactionFilter;
